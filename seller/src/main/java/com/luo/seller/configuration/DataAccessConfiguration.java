package com.luo.seller.configuration;

import com.luo.entity.Order;
import com.luo.seller.repobackup.VerifyRepository;
import com.luo.seller.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
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

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    //备份数据库
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.backup")
    public DataSource backupDataSource() {
        return DataSourceBuilder.create().build();
    }

    //配置 entityManagerFactory ，必须配置 一个名字 叫entityManagerFactory ，否则报错
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("primaryDataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("primary")
                .build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean backupCustomerEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("backupDataSource") DataSource dataSource
    ) {
        return builder.dataSource(dataSource)
                .packages(Order.class)
                .properties(getVendorProperties(dataSource))
                .persistenceUnit("backup")
                .build();
    }

    protected Map<String, Object> getVendorProperties(DataSource dataSource) {
        Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
        vendorProperties.putAll(jpaProperties.getHibernateProperties(dataSource));
        return vendorProperties;
    }

    //tx
    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
    public PlatformTransactionManager primaryTransactionManager(@Qualifier("entityManagerFactory")
                                                                        LocalContainerEntityManagerFactoryBean primaryCustomerEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(primaryCustomerEntityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    @ConditionalOnMissingBean(PlatformTransactionManager.class)
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

    /*@EnableJpaRepositories(basePackageClasses = OrderRepository.class,
            entityManagerFactoryRef = "backupEntityManagerFactory",transactionManagerRef = "backupTransactionManager")
    @RepositoryBeanNamePrefix("read")
    public class ReadConfiguration {
    }*/

    @EnableJpaRepositories(basePackageClasses = VerifyRepository.class,
            entityManagerFactoryRef = "backupCustomerEntityManagerFactory", transactionManagerRef = "backupTransactionManager")
    public class BackupConfiguration {
    }
}
