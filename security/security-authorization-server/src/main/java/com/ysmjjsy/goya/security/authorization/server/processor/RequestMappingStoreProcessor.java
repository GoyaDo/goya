package com.ysmjjsy.goya.security.authorization.server.processor;

import com.ysmjjsy.goya.component.web.scan.RequestMappingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 09:12
 */
@Slf4j
@RequiredArgsConstructor
public class RequestMappingStoreProcessor {

    private final SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor;

    @Async
    public void postProcess(RequestMappingEvent requestMappings) {
        log.debug("[Goya] |- [4] Async store request mapping process BEGIN!");
        securityMetadataDistributeProcessor.postRequestMappings(requestMappings);
    }
}
