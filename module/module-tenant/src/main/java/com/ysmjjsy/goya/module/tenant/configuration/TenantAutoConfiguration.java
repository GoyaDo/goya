package com.ysmjjsy.goya.module.tenant.configuration;

import com.ysmjjsy.goya.module.tenant.config.TenantConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/31 11:39
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@Import({TenantConfiguration.class})
public class TenantAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [tenant] TenantAutoConfiguration auto configure.");
    }

}
