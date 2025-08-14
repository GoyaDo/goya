package com.ysmjjsy.goya.component.secure.configuration;

import com.ysmjjsy.goya.component.secure.interceptor.AccessLimitedInterceptor;
import com.ysmjjsy.goya.component.secure.interceptor.IdempotentInterceptor;
import com.ysmjjsy.goya.component.web.configuration.WebMvcAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 11:36
 */
@Slf4j
@AutoConfiguration(after = {SecureAutoConfiguration.class, WebMvcAutoConfiguration.class})
@ConditionalOnWebApplication
public class WebSecureAutoConfiguration implements WebMvcConfigurer {

    private final ObjectProvider<IdempotentInterceptor> idempotent;
    private final ObjectProvider<AccessLimitedInterceptor> accessLimited;

    public WebSecureAutoConfiguration(ObjectProvider<IdempotentInterceptor> idempotent,
                                      ObjectProvider<AccessLimitedInterceptor> accessLimited) {
        this.idempotent = idempotent;
        this.accessLimited = accessLimited;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        accessLimited.ifAvailable(registry::addInterceptor);
        idempotent.ifAvailable(registry::addInterceptor);
    }

}
