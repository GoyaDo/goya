package com.ysmjjsy.goya.module.redis;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 10:01
 */
@Slf4j
@AutoConfiguration
public class RedisAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- starter [module redis] auto configure.");
    }
}
