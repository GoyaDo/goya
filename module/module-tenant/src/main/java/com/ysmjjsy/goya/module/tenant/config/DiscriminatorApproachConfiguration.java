package com.ysmjjsy.goya.module.tenant.config;

import com.ysmjjsy.goya.module.tenant.GoyaTenantIdentifierResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 共享数据库，独立Schema，共享数据表多租户配置 </p>
 *
 * @author goya
 * @since 2023/3/28 22:26
 */
@Configuration(proxyBeanMethods = false)
public class DiscriminatorApproachConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DiscriminatorApproachConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [tenant] [discriminator approach] configure.");
    }

    @Bean
    public HibernatePropertiesCustomizer goyaTenantIdentifierResolver() {
        GoyaTenantIdentifierResolver resolver = new GoyaTenantIdentifierResolver();
        log.debug("[Goya] |- Bean [Current Tenant Identifier Resolver] Auto Configure.");
        return resolver;
    }
}
