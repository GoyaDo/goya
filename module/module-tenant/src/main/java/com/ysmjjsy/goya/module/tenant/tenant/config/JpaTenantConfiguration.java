package com.ysmjjsy.goya.module.tenant.tenant.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/7 11:31
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@Import(DiscriminatorApproachConfiguration.class)
public class JpaTenantConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [tenant] [jpa tenant] configure.");
    }

}
