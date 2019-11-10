package com.luo.seller.configuration;

import com.luo.entity.Order;
import com.luo.seller.repobackup.VerifyRepository;
import com.luo.seller.repositories.OrderRepository;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.RepositoryBeanNamePrefix;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 多数据源配置
 * 1.datasource
 * 2.entityManager
 * 3.tx
 */
@Configuration
public class DataAccessConfiguration {

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private ObjectProvider<List<SchemaManagementProvider>> providers;
    @Autowired
    private ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy;
    @Autowired
    private ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        // return DataSourceBuilder.create().build();
        return primaryDataSourceProperties().initializeDataSourceBuilder().build();
    }

    //备份数据库
    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSourceProperties backupDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.backup")
    public DataSource backupDataSource() {
        //return DataSourceBuilder.create().build();
        return backupDataSourceProperties().initializeDataSourceBuilder().build();
    }

    //配置 entityManagerFactory ，必须配置 一个名字 叫entityManagerFactory ，否则报错
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean backupCustomerEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("backupDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup")
                .build();
    }

    protected Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
        //vendorProperties.putAll(jpaProperties.getHibernateProperties(dataSource));
        String defaultDdlMode = new HibernateDefaultDdlAutoProvider(
                providers.getIfAvailable(Collections::emptyList))
                .getDefaultDdlAuto(dataSource);
        vendorProperties.putAll(jpaProperties.getHibernateProperties(
                new HibernateSettings().ddlAuto(defaultDdlMode).physicalNamingStrategy(physicalNamingStrategy.getIfAvailable())
                        .implicitNamingStrategy(implicitNamingStrategy.getIfAvailable())
        ));
        return vendorProperties;
    }

    //tx:名字必须 是 transactionManager；参数名必须是 entityManagerFactory
    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory")
                                                                 LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager backupTransactionManager(@Qualifier("backupCustomerEntityManagerFactory")
                                                                       LocalContainerEntityManagerFactoryBean backupCustomerEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(backupCustomerEntityManagerFactory.getObject());
        return transactionManager;
    }

    // repository 扫描的时候，并不确定哪个先扫描，查看源代码,backup 先被扫描到
    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "primaryCustomerEntityManagerFactory", transactionManagerRef = "primaryTransactionManager")
    @Primary
    public class PrimaryConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "backupCustomerEntityManagerFactory", transactionManagerRef = "backupTransactionManager")
    @RepositoryBeanNamePrefix("read")
    public class ReadConfiguration {
    }

    @EnableJpaRepositories(basePackageClasses = VerifyRepository.class,
            entityManagerFactoryRef = "backupCustomerEntityManagerFactory", transactionManagerRef = "backupTransactionManager")
    public class BackupConfiguration {
    }

    class HibernateDefaultDdlAutoProvider implements SchemaManagementProvider {

        private final List<SchemaManagementProvider> providers;

        HibernateDefaultDdlAutoProvider(List<SchemaManagementProvider> providers) {
            this.providers = providers;
        }

        public String getDefaultDdlAuto(DataSource dataSource) {
            if (!EmbeddedDatabaseConnection.isEmbedded(dataSource)) {
                return "none";
            }
            SchemaManagement schemaManagement = getSchemaManagement(dataSource);
            if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                return "none";
            }
            return "create-drop";

        }

        @Override
        public SchemaManagement getSchemaManagement(DataSource dataSource) {
            for (SchemaManagementProvider provider : this.providers) {
                SchemaManagement schemaManagement = provider.getSchemaManagement(dataSource);
                if (SchemaManagement.MANAGED.equals(schemaManagement)) {
                    return schemaManagement;
                }
            }
            return SchemaManagement.UNMANAGED;
        }

    }

}
