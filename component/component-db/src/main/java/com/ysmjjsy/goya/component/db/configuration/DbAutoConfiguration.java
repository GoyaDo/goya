package com.ysmjjsy.goya.component.db.configuration;

import com.ysmjjsy.goya.component.db.configuration.properties.DbProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:26
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({DbProperties.class})
public class DbAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [db] DbAutoConfiguration auto configure.");
    }
}
