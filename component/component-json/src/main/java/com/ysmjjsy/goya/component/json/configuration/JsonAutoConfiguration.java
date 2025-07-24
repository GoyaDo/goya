package com.ysmjjsy.goya.component.json.configuration;

import com.ysmjjsy.goya.component.json.jackson2.Jackson2DefaultObjectMapperBuilderCustomizer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>Json自动配置</p>
 *
 * @author goya
 * @since 2025/7/24 23:07
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ComponentScan({
        "com.ysmjjsy.goya.component.json.jackson2.utils"
})
public class JsonAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [json] JsonAutoConfiguration auto configure.");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.trace("[Goya] |- component [json] |- bean [defaultObjectMapperBuilderCustomizer] register.");
        return customizer;
    }

}
