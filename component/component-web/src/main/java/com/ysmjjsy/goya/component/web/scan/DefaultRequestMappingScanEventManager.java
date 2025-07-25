package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * <p>Description: 自定义 RequestMappingScanManager</p>
 *
 * @author goya
 * @since 2022/1/17 0:08
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultRequestMappingScanEventManager implements RequestMappingScanEventManager {

    private final Class<? extends Annotation> scanAnnotationClass;

    @Override
    public Class<? extends Annotation> getScanAnnotationClass() {
        return scanAnnotationClass;
    }

    @Override
    public void postLocalStorage(List<RequestMapping> requestMappings) {
        log.info("[Goya] |- [4] Async store request mapping process BEGIN!");
    }

    @Override
    public String getDestinationServiceName() {
        return GoyaContextHolder.getInstance().getAuthServiceName();
    }

    @Override
    public void postLocalProcess(List<RequestMapping> data) {
        publishEvent(new RequestMappingEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        log.info("request mapping remote process not support!");
    }
}
