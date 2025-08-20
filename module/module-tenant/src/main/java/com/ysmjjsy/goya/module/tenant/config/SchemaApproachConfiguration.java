package com.ysmjjsy.goya.module.tenant.config;

import com.ysmjjsy.goya.module.tenant.tenant.SchemaMultiTenantConnectionProvider;
import com.ysmjjsy.goya.module.tenant.annotation.ConditionalOnSchemaApproach;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p>Description: 共享数据库，独立Schema多租户方式配置 </p>
 *
 * @author goya
 * @since 2023/3/28 22:26
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSchemaApproach
public class SchemaApproachConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SchemaApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [tenant] [schema approach] Configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer schemaMultiTenantConnectionProvider(DataSource dataSource) {
        SchemaMultiTenantConnectionProvider provider = new SchemaMultiTenantConnectionProvider(dataSource);
        log.debug("[Goya] |- Bean [Multi Tenant Connection Provider] Auto Configure.");
        return provider;
    }
}
