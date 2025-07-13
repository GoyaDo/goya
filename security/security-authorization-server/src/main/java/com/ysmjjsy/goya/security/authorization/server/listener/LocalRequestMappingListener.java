package com.ysmjjsy.goya.security.authorization.server.listener;

import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.component.web.scan.RequestMappingEvent;
import com.ysmjjsy.goya.security.authorization.server.processor.RequestMappingStoreProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 09:11
 */
@Slf4j
@RequiredArgsConstructor
public class LocalRequestMappingListener implements ApplicationListener<RequestMappingEvent> {

    private final RequestMappingStoreProcessor requestMappingStoreProcessor;

    @Override
    public void onApplicationEvent(RequestMappingEvent event) {
        log.info("[Goya] |- Request mapping gather LOCAL listener, response event!");

        List<RequestMapping> requestMappings = event.getData();
        if (CollectionUtils.isNotEmpty(requestMappings)) {
            requestMappingStoreProcessor.postProcess(requestMappings);
        }
    }
}
