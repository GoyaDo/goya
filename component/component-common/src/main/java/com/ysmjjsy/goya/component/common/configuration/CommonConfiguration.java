package com.ysmjjsy.goya.component.common.configuration;

import com.ysmjjsy.goya.component.common.json.jackson2.Jackson2DefaultObjectMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:43
 */
@Slf4j
@AutoConfiguration
@Import({
        SpringUtil.class,
})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class CommonConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [common] configure.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.debug("[Goya] |- Strategy [Jackson2 Default ObjectMapper Builder Customizer] Configure.");
        return customizer;
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({
            "com.ysmjjsy.goya.component.common.json.jackson2.utils"
    })
    static class JacksonUtilsConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Goya] |- component [jackson2] configure.");
        }
    }
}
