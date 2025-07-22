package com.ysmjjsy.goya.module.kafka.configuration;

import com.ysmjjsy.goya.component.web.scan.RequestMappingScanEventManager;
import com.ysmjjsy.goya.module.kafka.account.RemoteAccountStatusEventManager;
import com.ysmjjsy.goya.module.kafka.scan.RemoteSecurityDefaultRequestMappingScanEventManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.AccountStatusEventManager;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 00:02
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@RemoteApplicationEventScan({
        "com.ysmjjsy.goya.module.kafka.bus"
})
public class KafkaConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- Module [kafka] Configure.");
    }

    @Bean
    @Order(Integer.MAX_VALUE - 2)
    @ConditionalOnBean(RequestMappingScanEventManager.class)
    public RequestMappingScanEventManager requestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        RemoteSecurityDefaultRequestMappingScanEventManager requestMappingScanEventManager = new RemoteSecurityDefaultRequestMappingScanEventManager(securityAttributeAnalyzer);
        log.trace("[Goya] |- Bean [Servlet Remote Security Request Mapping Scan Event Manager] Configure.");
        return requestMappingScanEventManager;
    }

    @Bean
    @Order(Integer.MAX_VALUE - 2)
    @ConditionalOnBean(AccountStatusEventManager.class)
    public AccountStatusEventManager accountStatusEventManager() {
        RemoteAccountStatusEventManager remoteAccountStatusEventManager = new RemoteAccountStatusEventManager();
        log.trace("[Goya] |- Bean [Remote Account Status Event Manager] Configure.");
        return remoteAccountStatusEventManager;
    }

}
