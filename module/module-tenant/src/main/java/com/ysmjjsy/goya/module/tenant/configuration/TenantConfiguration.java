package com.ysmjjsy.goya.module.tenant.configuration;

import com.ysmjjsy.goya.module.tenant.config.DatabaseApproachConfiguration;
import com.ysmjjsy.goya.module.tenant.config.DiscriminatorApproachConfiguration;
import com.ysmjjsy.goya.module.tenant.config.SchemaApproachConfiguration;
import com.ysmjjsy.goya.module.tenant.interceptor.MultiTenantInterceptor;
import com.ysmjjsy.goya.module.tenant.properties.MultiTenantProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 18:11
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(MultiTenantProperties.class)
@Import({
        DiscriminatorApproachConfiguration.class,
        SchemaApproachConfiguration.class,
        DatabaseApproachConfiguration.class,
})
@RequiredArgsConstructor
public class TenantConfiguration implements WebMvcConfigurer {

    private final MultiTenantInterceptor multiTenantInterceptor;


    @PostConstruct
    public void postConstruct() {
        log.info("[Goya] |- component [jpa tenant] configure.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(multiTenantInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public MultiTenantInterceptor tenantInterceptor() {
        MultiTenantInterceptor multiTenantInterceptor = new MultiTenantInterceptor();
        log.trace("[Goya] |- Bean [Idempotent Interceptor] Configure.");
        return multiTenantInterceptor;
    }
}
