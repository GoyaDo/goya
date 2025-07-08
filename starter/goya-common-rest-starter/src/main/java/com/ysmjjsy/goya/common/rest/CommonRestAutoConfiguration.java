package com.ysmjjsy.goya.common.rest;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 13:12
 */
@Slf4j
@AutoConfiguration
public class CommonRestAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- starter [common rest] auto configure.");
    }
}
