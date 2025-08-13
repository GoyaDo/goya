package com.ysmjjsy.goya.component.web.configuration;

import com.google.common.collect.ImmutableList;
import com.ysmjjsy.goya.component.cache.configuration.CacheAutoConfiguration;
import com.ysmjjsy.goya.component.common.pojo.constants.GoyaConstants;
import com.ysmjjsy.goya.component.common.strategy.Singleton;
import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.component.core.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.doc.server.OpenApiServerResolver;
import com.ysmjjsy.goya.component.web.advice.ServletRestControllerAdvice;
import com.ysmjjsy.goya.component.web.configuration.properties.PlatformProperties;
import com.ysmjjsy.goya.component.web.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import com.ysmjjsy.goya.component.web.initializer.GoyaContextHolderBuilder;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

/**
 * <p>Description: Web 服务通用配置 </p>
 *
 * @author goya
 * @since 2024/1/24 16:34
 */
@Slf4j
@AutoConfiguration
@AutoConfigureAfter(CacheAutoConfiguration.class)
@EnableConfigurationProperties({PlatformProperties.class})
@Import(ServletRestControllerAdvice.class)
public class WebServiceAutoConfiguration implements ApplicationContextAware {

    private final ServiceContextHolder serviceContextHolder;

    /**
     * 使用构造函数的方式，可以确保时机正确，几个参数对象设置正确，最终保证 ServiceContextHolder 的初始化时机合理
     *
     * @param platformProperties {@link PlatformProperties}
     * @param serverProperties   {@link ServerProperties}
     */
    public WebServiceAutoConfiguration(PlatformProperties platformProperties, ServerProperties serverProperties) {
        this.serviceContextHolder = GoyaContextHolderBuilder.builder()
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebServiceConfiguration configure.");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.serviceContextHolder.setApplicationContext(applicationContext);
        this.serviceContextHolder.setApplicationName(PropertyResolver.getProperty(applicationContext.getEnvironment(), GoyaConstants.ITEM_SPRING_APPLICATION_NAME));
        log.trace("[Goya] |- component [web] |- Goya ApplicationContext initialization completed.");
        Singleton.put(this.serviceContextHolder);
    }

    @Bean
    @Primary
    public OpenApiServerResolver openApiServerResolver() {
        OpenApiServerResolver resolver = () -> {
            Server server = new Server();
            server.setUrl(serviceContextHolder.getUrl());
            return ImmutableList.of(server);
        };
        log.trace("[Goya] |- component [doc] |- bean [openApiServerResolver] register.");
        return resolver;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.trace("[Goya] |- component [web] |- bean [xssObjectMapperBuilderCustomizer] register.");
        return customizer;
    }
}
