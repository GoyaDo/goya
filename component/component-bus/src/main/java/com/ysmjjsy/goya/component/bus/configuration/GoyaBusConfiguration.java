package com.ysmjjsy.goya.component.bus.configuration;

import com.ysmjjsy.goya.component.bus.api.GoyaBus;
import com.ysmjjsy.goya.component.bus.core.DefaultGoyaEventBus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/10 22:31
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class GoyaBusConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [bus] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public GoyaBus goyaBus() {
        DefaultGoyaEventBus bus = new DefaultGoyaEventBus();
        log.trace("[Goya] |- component [bus] |- bean [goyaBus] register.");
        return bus;
    }
}
