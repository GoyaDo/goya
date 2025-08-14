package com.ysmjjsy.goya.component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 13:16
 */
@Slf4j
@AutoConfiguration
public class ComponentAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- starter [component] ComponentAutoConfiguration auto configure.");
    }
}
