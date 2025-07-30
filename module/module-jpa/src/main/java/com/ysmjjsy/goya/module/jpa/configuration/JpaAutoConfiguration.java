package com.ysmjjsy.goya.module.jpa.configuration;

import com.ysmjjsy.goya.module.jpa.auditing.SecurityAuditorAware;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 23:39
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
public class JpaAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [jpa] JpaAutoConfiguration auto configure.");
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        SecurityAuditorAware securityAuditorAware = new SecurityAuditorAware();
        log.trace("[Goya] |- module [jpa] |- bean [auditorAware] register.");
        return securityAuditorAware;
    }

}
