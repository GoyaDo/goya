package com.ysmjjsy.goya.component.db.configuration;

import com.ysmjjsy.goya.component.db.configuration.properties.DbProperties;
import com.ysmjjsy.goya.component.distributedid.configuration.DistributedIdAutoConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:26
 */
@Slf4j
@AutoConfiguration(after = {CacheAutoConfiguration.class, DistributedIdAutoConfiguration.class})
@EnableConfigurationProperties({DbProperties.class})
public class GoyaDbAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [db] DbAutoConfiguration auto configure.");
    }
}
