package com.ysmjjsy.goya.security.authorization.server;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:31
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class SecurityServerAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Goya] |- starter [security server] auto configure.");
    }
}
