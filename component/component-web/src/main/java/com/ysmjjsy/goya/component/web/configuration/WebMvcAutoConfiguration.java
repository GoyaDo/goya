package com.ysmjjsy.goya.component.web.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.LiteWebJarsResourceResolver;

/**
 * <p>Description </p>
 *
 * @author goya
 * @since 2024/4/10 9:29
 */
@Slf4j
@AutoConfiguration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebMvcAutoConfiguration auto configure.");
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
