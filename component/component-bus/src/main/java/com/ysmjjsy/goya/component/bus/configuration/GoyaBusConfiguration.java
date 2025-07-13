package com.ysmjjsy.goya.component.bus.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

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
}
