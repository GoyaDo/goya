package com.ysmjjsy.goya.module.rest.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 16:44
 */
@Slf4j
@AutoConfiguration
public class RestConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [rest] configure.");
    }


}
