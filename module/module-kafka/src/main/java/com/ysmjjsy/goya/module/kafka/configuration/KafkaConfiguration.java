package com.ysmjjsy.goya.module.kafka.configuration;

import com.ysmjjsy.goya.component.web.scan.RequestMappingScanEventManager;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import com.ysmjjsy.goya.security.authorization.scan.SecurityDefaultRequestMappingScanEventManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    @ConditionalOnMissingBean
    public RequestMappingScanEventManager requestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        SecurityDefaultRequestMappingScanEventManager requestMappingScanEventManager = new SecurityDefaultRequestMappingScanEventManager(securityAttributeAnalyzer);
        log.trace("[Goya] |- Bean [Servlet Remote Security Request Mapping Scan Event Manager] Configure.");
        return requestMappingScanEventManager;
    }

}
