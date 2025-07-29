package com.ysmjjsy.goya.component.web.config;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import com.ysmjjsy.goya.component.web.advice.ServletRestControllerAdvice;
import com.ysmjjsy.goya.component.web.customizer.Jackson2XssObjectMapperBuilderCustomizer;
import com.ysmjjsy.goya.component.web.initializer.GoyaContextHolderBuilder;
import com.ysmjjsy.goya.component.web.properties.PlatformProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>Description: Web 服务通用配置 </p>
 *
 * @author goya
 * @since 2024/1/24 16:34
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({PlatformProperties.class})
@Import(ServletRestControllerAdvice.class)
public class WebServiceConfiguration implements ApplicationContextAware {

    private final GoyaContextHolder goyaContextHolder;

    /**
     * 使用构造函数的方式，可以确保时机正确，几个参数对象设置正确，最终保证 ServiceContextHolder 的初始化时机合理
     *
     * @param platformProperties {@link PlatformProperties}
     * @param serverProperties   {@link ServerProperties}
     */
    public WebServiceConfiguration(PlatformProperties platformProperties,  ServerProperties serverProperties) {
        this.goyaContextHolder = GoyaContextHolderBuilder.builder()
                .platformProperties(platformProperties)
                .serverProperties(serverProperties)
                .build();
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web service] configure.");
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.goyaContextHolder.setApplicationContext(applicationContext);
        this.goyaContextHolder.setApplicationName(PropertyResolver.getProperty(applicationContext.getEnvironment(), GoyaConstants.ITEM_SPRING_APPLICATION_NAME));
        log.debug("[Goya] |- Goya ApplicationContext initialization completed.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssObjectMapperBuilderCustomizer() {
        Jackson2XssObjectMapperBuilderCustomizer customizer = new Jackson2XssObjectMapperBuilderCustomizer();
        log.debug("[Goya] |- Strategy [Jackson2 Xss ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }
}
