package com.ysmjjsy.goya.component.web.configuration;

import com.ysmjjsy.goya.component.web.config.SecureConfiguration;
import com.ysmjjsy.goya.component.web.config.WebServiceConfiguration;
import com.ysmjjsy.goya.component.web.secure.AccessLimitedInterceptor;
import com.ysmjjsy.goya.component.web.secure.IdempotentInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
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
@AutoConfiguration
@EnableWebMvc
@RequiredArgsConstructor
@Import({SecureConfiguration.class, WebServiceConfiguration.class})
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcAutoConfiguration.class);

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
}
