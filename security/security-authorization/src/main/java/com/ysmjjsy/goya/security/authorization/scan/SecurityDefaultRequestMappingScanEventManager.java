package com.ysmjjsy.goya.security.authorization.scan;

import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.component.web.scan.RequestMappingEvent;
import com.ysmjjsy.goya.component.web.scan.RequestMappingScanEventManager;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAttributeAnalyzer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.Annotation;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/10 23:10
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityDefaultRequestMappingScanEventManager implements RequestMappingScanEventManager {

    private final SecurityAttributeAnalyzer securityAttributeAnalyzer;

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return EnableWebSecurity.class;
    }

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getInstance().getAuthServiceName();
    }

    @Override
    public void postLocalStorage(RequestMappingEvent requestMappings) {
        securityAttributeAnalyzer.processRequestMatchers();
    }

    @Override
    public void postLocalProcess(RequestMappingEvent data) {
        publishEvent(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        log.info("request mapping remote process not support!");
    }
}
