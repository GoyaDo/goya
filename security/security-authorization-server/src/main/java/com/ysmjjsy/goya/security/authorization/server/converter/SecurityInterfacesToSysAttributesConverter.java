package com.ysmjjsy.goya.security.authorization.server.converter;

import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: List<SecurityInterface> 转 List<SecurityAttribute> 转换器 </p>
 *
 * @author goya
 * @since 2023/8/23 22:59
 */
public class SecurityInterfacesToSysAttributesConverter implements Converter<List<SecurityInterface>, List<SecurityAttribute>> {
    @Override
    public List<SecurityAttribute> convert(List<SecurityInterface> sysInterfaces) {
        if (CollectionUtils.isNotEmpty(sysInterfaces)) {
            return sysInterfaces.stream().map(this::convert).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private SecurityAttribute convert(SecurityInterface sysInterface) {
        SecurityAttribute sysAttribute = new SecurityAttribute();
        sysAttribute.setId(sysInterface.getId());
        sysAttribute.setAttributeCode(sysInterface.getInterfaceCode());
        sysAttribute.setRequestMethod(sysInterface.getRequestMethod());
        sysAttribute.setServiceId(sysInterface.getServiceId());
        sysAttribute.setClassName(sysInterface.getClassName());
        sysAttribute.setMethodName(sysInterface.getMethodName());
        sysAttribute.setUrl(sysInterface.getUrl());
        sysAttribute.setDescription(sysInterface.getDescription());
        sysAttribute.setDelFlag(sysInterface.getDelFlag());
        sysAttribute.setCreateBy(sysInterface.getCreateBy());
        sysAttribute.setUpdateBy(sysInterface.getUpdateBy());
        return sysAttribute;
    }

}
