package com.ysmjjsy.goya.module.kafka.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;

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

//    @Bean
//    @Order(Integer.MAX_VALUE - 2)
//    @ConditionalOnMissingBean
//    public RequestMappingScanEventManager requestMappingScanEventManager(SecurityAttributeAnalyzer securityAttributeAnalyzer) {
//        SecurityDefaultRequestMappingScanEventManager requestMappingScanEventManager = new SecurityDefaultRequestMappingScanEventManager(securityAttributeAnalyzer);
//        log.trace("[Goya] |- Bean [Servlet Remote Security Request Mapping Scan Event Manager] Configure.");
//        return requestMappingScanEventManager;
//    }

}
