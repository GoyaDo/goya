package com.ysmjjsy.goya.component.db.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:26
 */
@Slf4j
@EnableJpaAuditing
@AutoConfiguration
public class JpaConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [jpa] configure.");
    }
}
