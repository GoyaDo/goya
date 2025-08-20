package com.ysmjjsy.goya.security.authorization.server.configuration;

import com.ysmjjsy.goya.module.jpa.annotation.EnableGoyaJpaRepositories;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityAttributeService;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityInterfaceService;
import com.ysmjjsy.goya.security.authorization.server.listener.LocalRequestMappingListener;
import com.ysmjjsy.goya.security.authorization.server.listener.SecurityAttributeChangeListener;
import com.ysmjjsy.goya.security.authorization.server.listener.SecurityAttributeEntityListener;
import com.ysmjjsy.goya.security.authorization.server.processor.RequestMappingStoreProcessor;
import com.ysmjjsy.goya.security.authorization.server.processor.SecurityMetadataDistributeProcessor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 22:56
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EntityScan(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.entity"
})
@EnableGoyaJpaRepositories(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.repository"
})
@ComponentScan(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.service",
})
public class SecurityRequestScanConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security authorization request scan] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor(SecurityAttributeService sysAttributeService,
                                                                                   SecurityInterfaceService sysInterfaceService,
                                                                                   SecurityAttributeAnalyzer securityAttributeAnalyzer) {
        return new SecurityMetadataDistributeProcessor(sysAttributeService, sysInterfaceService, securityAttributeAnalyzer);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingStoreProcessor requestMappingStoreProcessor(SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor) {
        return new RequestMappingStoreProcessor(securityMetadataDistributeProcessor);
    }

    @Bean
    public SecurityAttributeEntityListener securityAttributeEntityListener() {
        return new SecurityAttributeEntityListener();
    }

    @Bean
    public SecurityAttributeChangeListener securityAttributeChangeListener(SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor) {
        return new SecurityAttributeChangeListener(securityMetadataDistributeProcessor);
    }

    @Bean
    public LocalRequestMappingListener localRequestMappingListener(RequestMappingStoreProcessor requestMappingStoreProcessor) {
        return new LocalRequestMappingListener(requestMappingStoreProcessor);
    }
}
