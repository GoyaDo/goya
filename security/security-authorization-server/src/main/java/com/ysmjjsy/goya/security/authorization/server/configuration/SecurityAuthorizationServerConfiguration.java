package com.ysmjjsy.goya.security.authorization.server.configuration;

import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityAttributeService;
import com.ysmjjsy.goya.security.authorization.server.domain.service.SecurityInterfaceService;
import com.ysmjjsy.goya.security.authorization.server.listener.LocalRequestMappingListener;
import com.ysmjjsy.goya.security.authorization.server.listener.SecurityAttributeChangeListener;
import com.ysmjjsy.goya.security.authorization.server.listener.SecurityAttributeEntityListener;
import com.ysmjjsy.goya.security.authorization.server.processor.RequestMappingStoreProcessor;
import com.ysmjjsy.goya.security.authorization.server.processor.SecurityMetadataDistributeProcessor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:35
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EntityScan(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.repository"
})
@ComponentScan(basePackages = {
        "com.ysmjjsy.goya.security.authorization.server.domain.service",
})
public class SecurityAuthorizationServerConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security authorization server] Configure.");
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
