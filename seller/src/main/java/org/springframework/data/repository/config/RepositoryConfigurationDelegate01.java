/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.repository.config;

import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 当您的类名和源码里面的类名一样的时候，先用自己的类
 */
public class RepositoryConfigurationDelegate01 {
 /*
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfigurationDelegate.class);

    private static final String REPOSITORY_REGISTRATION = "Spring Data {} - Registering repository: {} - Interface: {} - Factory: {}";
    private static final String MULTIPLE_MODULES = "Multiple Spring Data modules found, entering strict repository configuration mode!";

    static final String FACTORY_BEAN_OBJECT_TYPE = "factoryBeanObjectType";

    private final RepositoryConfigurationSource configurationSource;
    private final ResourceLoader resourceLoader;
    private final Environment environment;
    private final BeanNameGenerator beanNameGenerator;
    private final boolean isXml;
    private final boolean inMultiStoreMode;

    *//**
     * Creates a new {@link RepositoryConfigurationDelegate} for the given {@link RepositoryConfigurationSource} and
     * {@link ResourceLoader} and {@link Environment}.
     *
     * @param configurationSource must not be {@literal null}.
     * @param resourceLoader      must not be {@literal null}.
     * @param environment         must not be {@literal null}.
     *//*
    public RepositoryConfigurationDelegate(RepositoryConfigurationSource configurationSource,
                                           ResourceLoader resourceLoader, Environment environment) {

        this.isXml = configurationSource instanceof XmlRepositoryConfigurationSource;
        boolean isAnnotation = configurationSource instanceof AnnotationRepositoryConfigurationSource;

        Assert.isTrue(isXml || isAnnotation,
                "Configuration source must either be an Xml- or an AnnotationBasedConfigurationSource!");
        Assert.notNull(resourceLoader, "ResourceLoader must not be null!");

        RepositoryBeanNameGenerator generator = new RepositoryBeanNameGenerator();
        generator.setBeanClassLoader(resourceLoader.getClassLoader());

        this.beanNameGenerator = generator;
        this.configurationSource = configurationSource;
        this.resourceLoader = resourceLoader;
        this.environment = defaultEnvironment(environment, resourceLoader);
        this.inMultiStoreMode = multipleStoresDetected();
    }

    *//**
     * Defaults the environment in case the given one is null. Used as fallback, in case the legacy constructor was
     * invoked.
     *
     * @param environment    can be {@literal null}.
     * @param resourceLoader can be {@literal null}.
     * @return
     *//*
    private static Environment defaultEnvironment(Environment environment, ResourceLoader resourceLoader) {

        if (environment != null) {
            return environment;
        }

        return resourceLoader instanceof EnvironmentCapable ? ((EnvironmentCapable) resourceLoader).getEnvironment()
                : new StandardEnvironment();
    }

    *//**
     * Registers the found repositories in the given {@link BeanDefinitionRegistry}.
     *
     * @param registry
     * @param extension
     * @return {@link BeanComponentDefinition}s for all repository bean definitions found.
     *//*
    public List<BeanComponentDefinition> registerRepositoriesIn(BeanDefinitionRegistry registry,
                                                                RepositoryConfigurationExtension extension) {

        extension.registerBeansForRoot(registry, configurationSource);

        RepositoryBeanDefinitionBuilder builder = new RepositoryBeanDefinitionBuilder(registry, extension, resourceLoader,
                environment);
        List<BeanComponentDefinition> definitions = new ArrayList<BeanComponentDefinition>();

        for (RepositoryConfiguration<? extends RepositoryConfigurationSource> configuration : extension
                .getRepositoryConfigurations(configurationSource, resourceLoader, inMultiStoreMode)) {

            BeanDefinitionBuilder definitionBuilder = builder.build(configuration);

            extension.postProcess(definitionBuilder, configurationSource);

            if (isXml) {
                extension.postProcess(definitionBuilder, (XmlRepositoryConfigurationSource) configurationSource);
            } else {
                extension.postProcess(definitionBuilder, (AnnotationRepositoryConfigurationSource) configurationSource);
            }

            AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
            String beanName = beanNameGenerator.generateBeanName(beanDefinition, registry);

            //
            AnnotationMetadata metadata = (AnnotationMetadata) configurationSource.getSource();
            if (metadata.hasAnnotation(Primary.class.getName())) {
                beanDefinition.setPrimary(true);

            } else if (metadata.hasAnnotation(RepositoryBeanNamePrefix.class.getName())) {
                Map<String, Object> prefixData = metadata.getAnnotationAttributes(RepositoryBeanNamePrefix.class.getName());
                String prefix = (String) prefixData.get("value");
                beanName = prefix + beanName;//注入的 repo的名字


            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(REPOSITORY_REGISTRATION, extension.getModuleName(), beanName,
                        configuration.getRepositoryInterface(), extension.getRepositoryFactoryClassName());
            }

            beanDefinition.setAttribute(FACTORY_BEAN_OBJECT_TYPE, configuration.getRepositoryInterface());

            registry.registerBeanDefinition(beanName, beanDefinition);
            definitions.add(new BeanComponentDefinition(beanDefinition, beanName));
        }

        return definitions;
    }

    *//**
     * Scans {@code repository.support} packages for implementations of {@link RepositoryFactorySupport}. Finding more
     * than a single type is considered a multi-store configuration scenario which will trigger stricter repository
     * scanning.
     *
     * @return
     *//*
    private boolean multipleStoresDetected() {

        boolean multipleModulesFound = SpringFactoriesLoader
                .loadFactoryNames(RepositoryFactorySupport.class, resourceLoader.getClassLoader()).size() > 1;

        if (multipleModulesFound) {
            LOGGER.info(MULTIPLE_MODULES);
        }

        return multipleModulesFound;
    }
  */
}
