package com.ysmjjsy.goya.common;

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
public class CommonAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- starter [common] auto configure.");
    }
}
