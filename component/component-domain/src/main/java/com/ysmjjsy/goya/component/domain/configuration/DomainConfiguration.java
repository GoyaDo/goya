package com.ysmjjsy.goya.component.domain.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:30
 */
@Slf4j
@AutoConfiguration
public class DomainConfiguration {

    @PostConstruct
    public void init() {
        log.debug("[Goya] |- component [domain] configure.");
    }
}
