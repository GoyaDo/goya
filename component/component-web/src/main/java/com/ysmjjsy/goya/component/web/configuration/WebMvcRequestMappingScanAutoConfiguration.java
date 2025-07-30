package com.ysmjjsy.goya.component.web.configuration;

import com.ysmjjsy.goya.component.web.configuration.properties.ScanProperties;
import com.ysmjjsy.goya.component.web.scan.DefaultRequestMappingScanEventManager;
import com.ysmjjsy.goya.component.web.scan.RequestMappingScanEventManager;
import com.ysmjjsy.goya.component.web.scan.RequestMappingScanner;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * <p>Description: 接口扫描配置 </p>
 *
 * @author goya
 * @since 2022/1/16 18:40
 */
@AutoConfiguration
@ConditionalOnClass(RequestMappingScanEventManager.class)
@EnableConfigurationProperties(ScanProperties.class)
public class WebMvcRequestMappingScanAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebMvcRequestMappingScanAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebMvcRequestMappingScanAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanner requestMappingScanner(ScanProperties scanProperties, RequestMappingScanEventManager requestMappingScanManager) {
        RequestMappingScanner scanner = new RequestMappingScanner(scanProperties, requestMappingScanManager);
        log.trace("[Goya] |- component [web] |- bean [requestMappingScanner] register.");
        return scanner;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order
    public RequestMappingScanEventManager requestMappingScanEventManager() {
        DefaultRequestMappingScanEventManager requestMappingScanEventManager = new DefaultRequestMappingScanEventManager(EnableWebMvc.class);
        log.trace("[Goya] |- component [web] |- bean [requestMappingScanEventManager] register.");
        return requestMappingScanEventManager;
    }
}
