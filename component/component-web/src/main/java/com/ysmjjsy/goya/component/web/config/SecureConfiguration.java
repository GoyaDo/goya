package com.ysmjjsy.goya.component.web.config;

import com.ysmjjsy.goya.component.web.properties.SecureProperties;
import com.ysmjjsy.goya.component.web.secure.AccessLimitedInterceptor;
import com.ysmjjsy.goya.component.web.secure.IdempotentInterceptor;
import com.ysmjjsy.goya.component.web.secure.XssHttpServletFilter;
import com.ysmjjsy.goya.component.web.stamp.AccessLimitedStampManager;
import com.ysmjjsy.goya.component.web.stamp.IdempotentStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 接口安全配置 </p>
 *
 * @author goya
 * @since 2021/10/4 17:28
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecureProperties.class)
public class SecureConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecureConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [servlet secure] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public IdempotentStampManager idempotentStampManager(SecureProperties secureProperties) {
        IdempotentStampManager idempotentStampManager = new IdempotentStampManager(secureProperties);
        log.trace("[Goya] |- Bean [Idempotent Stamp Manager] Configure.");
        return idempotentStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public AccessLimitedStampManager accessLimitedStampManager(SecureProperties secureProperties) {
        AccessLimitedStampManager accessLimitedStampManager = new AccessLimitedStampManager(secureProperties);
        log.trace("[Goya] |- Bean [Access Limited Stamp Manager] Configure.");
        return accessLimitedStampManager;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(IdempotentStampManager.class)
    public IdempotentInterceptor idempotentInterceptor(IdempotentStampManager idempotentStampManager) {
        IdempotentInterceptor idempotentInterceptor = new IdempotentInterceptor();
        idempotentInterceptor.setIdempotentStampManager(idempotentStampManager);
        log.trace("[Goya] |- Bean [Idempotent Interceptor] Configure.");
        return idempotentInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(AccessLimitedStampManager.class)
    public AccessLimitedInterceptor accessLimitedInterceptor(AccessLimitedStampManager accessLimitedStampManager) {
        AccessLimitedInterceptor accessLimitedInterceptor = new AccessLimitedInterceptor();
        accessLimitedInterceptor.setAccessLimitedStampManager(accessLimitedStampManager);
        log.trace("[Goya] |- Bean [Access Limited Interceptor] Configure.");
        return accessLimitedInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public XssHttpServletFilter xssHttpServletFilter() {
        XssHttpServletFilter xssHttpServletFilter = new XssHttpServletFilter();
        log.trace("[Goya] |- Bean [Xss Http Servlet Filter] Configure.");
        return xssHttpServletFilter;
    }
}
