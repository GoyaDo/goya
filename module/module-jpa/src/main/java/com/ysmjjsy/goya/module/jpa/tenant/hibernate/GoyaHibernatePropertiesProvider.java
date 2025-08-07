package com.ysmjjsy.goya.module.jpa.tenant.hibernate;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.SchemaManagement;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

/**
 * <p>Description: HibernateProperties 提供者 </p>
 * <p>
 * 参考 {@code HibernateJpaConfiguration} 的配置方式，解决 Database 多租户模式下，定义 {@code LocalContainerEntityManagerFactoryBean} Bean 所需 Hibernate Properties 的生成
 *
 * @author goya
 * @since 2024/5/2 9:50
 */
public class GoyaHibernatePropertiesProvider {

    private final DataSource dataSource;

    private final HibernateProperties hibernateProperties;
    private final JpaProperties jpaProperties;

    private final HibernateDefaultDdlAutoProvider defaultDdlAutoProvider;

    private final List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    public GoyaHibernatePropertiesProvider(DataSource dataSource,
                                           HibernateProperties hibernateProperties,
                                           JpaProperties jpaProperties,
                                           ConfigurableListableBeanFactory beanFactory,
                                           ObjectProvider<SchemaManagementProvider> providers,
                                           ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy,
                                           ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy,
                                           ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        this.dataSource = dataSource;
        this.hibernateProperties = hibernateProperties;
        this.jpaProperties = jpaProperties;
        this.defaultDdlAutoProvider = new HibernateDefaultDdlAutoProvider(providers);
        this.hibernatePropertiesCustomizers = determineHibernatePropertiesCustomizers(
                physicalNamingStrategy.getIfAvailable(), implicitNamingStrategy.getIfAvailable(), beanFactory,
                hibernatePropertiesCustomizers.orderedStream().toList());
    }

    public Map<String, Object> getVendorProperties() {
        Supplier<String> defaultDdlMode = () -> this.defaultDdlAutoProvider.getDefaultDdlAuto(dataSource);
        return new LinkedHashMap<>(this.hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings().ddlAuto(defaultDdlMode)
                        .hibernatePropertiesCustomizers(this.hibernatePropertiesCustomizers)));
    }

    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(
            PhysicalNamingStrategy physicalNamingStrategy, ImplicitNamingStrategy implicitNamingStrategy,
            ConfigurableListableBeanFactory beanFactory,
            List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        List<HibernatePropertiesCustomizer> customizers = new ArrayList<>();
        if (ClassUtils.isPresent("org.hibernate.resource.beans.container.spi.BeanContainer",
                getClass().getClassLoader())) {
            customizers.add((properties) -> properties.put(AvailableSettings.BEAN_CONTAINER,
                    new SpringBeanContainer(beanFactory)));
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers
                    .add(new NamingStrategiesHibernatePropertiesCustomizer(physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }

    private record HibernateDefaultDdlAutoProvider(
            Iterable<SchemaManagementProvider> providers) implements SchemaManagementProvider {

        String getDefaultDdlAuto(DataSource dataSource) {
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
                return StreamSupport.stream(this.providers.spliterator(), false)
                        .map((provider) -> provider.getSchemaManagement(dataSource))
                        .filter(SchemaManagement.MANAGED::equals)
                        .findFirst()
                        .orElse(SchemaManagement.UNMANAGED);
            }
        }

    private record NamingStrategiesHibernatePropertiesCustomizer(PhysicalNamingStrategy physicalNamingStrategy,
                                                                 ImplicitNamingStrategy implicitNamingStrategy) implements HibernatePropertiesCustomizer {

        @Override
            public void customize(Map<String, Object> hibernateProperties) {
                if (this.physicalNamingStrategy != null) {
                    hibernateProperties.put("hibernate.physical_naming_strategy", this.physicalNamingStrategy);
                }
                if (this.implicitNamingStrategy != null) {
                    hibernateProperties.put("hibernate.implicit_naming_strategy", this.implicitNamingStrategy);
                }
            }

        }
}
