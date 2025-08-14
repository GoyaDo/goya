package com.ysmjjsy.goya.component.secure.configuration;

import com.ysmjjsy.goya.component.cache.configuration.CacheAutoConfiguration;
import com.ysmjjsy.goya.component.cache.core.jetcache.JetCacheCreateCacheFactory;
import com.ysmjjsy.goya.component.secure.configuration.properties.SecureProperties;
import com.ysmjjsy.goya.component.secure.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import com.ysmjjsy.goya.component.secure.filter.XssHttpServletFilter;
import com.ysmjjsy.goya.component.secure.interceptor.AccessLimitedInterceptor;
import com.ysmjjsy.goya.component.secure.interceptor.IdempotentInterceptor;
import com.ysmjjsy.goya.component.secure.stamp.AccessLimitedStampManager;
import com.ysmjjsy.goya.component.secure.stamp.IdempotentStampManager;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 11:07
 */
@Slf4j
@AutoConfiguration(after = CacheAutoConfiguration.class)
@EnableConfigurationProperties(SecureProperties.class)
public class SecureAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [secure] SecureAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JetCacheCreateCacheFactory.class)
    public IdempotentStampManager idempotentStampManager(SecureProperties secureProperties) {
        IdempotentStampManager idempotentStampManager = new IdempotentStampManager(secureProperties);
        log.trace("[Goya] |- component [web] |- bean [idempotentStampManager] register.");
        return idempotentStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JetCacheCreateCacheFactory.class)
    public AccessLimitedStampManager accessLimitedStampManager(SecureProperties secureProperties) {
        AccessLimitedStampManager accessLimitedStampManager = new AccessLimitedStampManager(secureProperties);
        log.trace("[Goya] |- component [web] |- bean [accessLimitedStampManager] register.");
        return accessLimitedStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(IdempotentStampManager.class)
    public IdempotentInterceptor idempotentInterceptor(IdempotentStampManager idempotentStampManager) {
        IdempotentInterceptor idempotentInterceptor = new IdempotentInterceptor();
        idempotentInterceptor.setIdempotentStampManager(idempotentStampManager);
        log.trace("[Goya] |- component [web] |- bean [idempotentInterceptor] register.");
        return idempotentInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AccessLimitedStampManager.class)
    public AccessLimitedInterceptor accessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        AccessLimitedInterceptor accessLimitedInterceptor = new AccessLimitedInterceptor();
        accessLimitedInterceptor.setAccessLimitedStampManager(accessLimitedStampManager);
        log.trace("[Goya] |- component [web] |- bean [accessLimitedInterceptor] register.");
        return accessLimitedInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public XssHttpServletFilter xssHttpServletFilter() {
        XssHttpServletFilter xssHttpServletFilter = new XssHttpServletFilter();
        log.trace("[Goya] |- component [web] |- bean [xssHttpServletFilter] register.");
        return xssHttpServletFilter;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.trace("[Goya] |- component [web] |- bean [xssObjectMapperBuilderCustomizer] register.");
        return customizer;
    }
}
