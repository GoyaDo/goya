package com.ysmjjsy.goya.component.web.configuration;

import com.ysmjjsy.goya.component.cache.configuration.GoyaCacheAutoConfiguration;
import com.ysmjjsy.goya.component.web.config.SecureConfiguration;
import com.ysmjjsy.goya.component.web.secure.AccessLimitedInterceptor;
import com.ysmjjsy.goya.component.web.secure.IdempotentInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.LiteWebJarsResourceResolver;

/**
 * <p>Description </p>
 *
 * @author goya
 * @since 2024/4/10 9:29
 */
@AutoConfiguration(after = {org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration.class, RestTemplateAutoConfiguration.class})
@AutoConfigureAfter(GoyaCacheAutoConfiguration.class)
@EnableWebMvc
@RequiredArgsConstructor
@Import({SecureConfiguration.class})
public class WebAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebAutoConfiguration.class);

    private final IdempotentInterceptor idempotentInterceptor;
    private final AccessLimitedInterceptor accessLimitedInterceptor;


    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebMvcConfiguration auto configure.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitedInterceptor);
        registry.addInterceptor(idempotentInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addResolver(new LiteWebJarsResourceResolver());
    }
    @Bean
    @ConditionalOnMissingBean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder.build();
        log.trace("[Goya] |- component [web] |- bean [RestClient] Configure.");
        return restClient;
    }
}
