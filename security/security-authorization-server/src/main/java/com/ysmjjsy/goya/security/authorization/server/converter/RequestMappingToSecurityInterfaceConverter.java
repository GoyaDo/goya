package com.ysmjjsy.goya.security.authorization.server.converter;

import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityInterface;
import org.springframework.core.convert.converter.Converter;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:33
 */
public class RequestMappingToSecurityInterfaceConverter implements Converter<RequestMapping, SecurityInterface> {

    @Override
    public SecurityInterface convert(RequestMapping requestMapping) {
        SecurityInterface sysInterface = new SecurityInterface();
        sysInterface.setId(requestMapping.getMappingId());
        sysInterface.setInterfaceCode(requestMapping.getMappingCode());
        sysInterface.setRequestMethod(requestMapping.getRequestMethod());
        sysInterface.setServiceId(requestMapping.getServiceId());
        sysInterface.setClassName(requestMapping.getClassName());
        sysInterface.setMethodName(requestMapping.getMethodName());
        sysInterface.setUrl(requestMapping.getUrl());
        sysInterface.setDescription(requestMapping.getDescription());
        return sysInterface;
    }
}
