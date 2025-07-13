package com.ysmjjsy.goya.security.authorization.server.converter;

import com.ysmjjsy.goya.security.authorization.domain.AttributeTransmitter;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityPermission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * <p>Description: SecurityAttribute 转 AttributeTransmitter 转换器 </p>
 *
 * @author goya
 * @since 2023/9/15 0:29
 */
public class SecurityAttributeToAttributeTransmitterConverter implements Converter<SecurityAttribute, AttributeTransmitter> {
    @Override
    public AttributeTransmitter convert(SecurityAttribute sysAttribute) {
        AttributeTransmitter securityAttribute = new AttributeTransmitter();
        securityAttribute.setAttributeId(sysAttribute.getId());
        securityAttribute.setAttributeCode(sysAttribute.getAttributeCode());
        securityAttribute.setWebExpression(sysAttribute.getWebExpression());
        securityAttribute.setPermissions(permissionToCommaDelimitedString(sysAttribute.getPermissions()));
        securityAttribute.setUrl(sysAttribute.getUrl());
        securityAttribute.setRequestMethod(sysAttribute.getRequestMethod());
        securityAttribute.setServiceId(sysAttribute.getServiceId());
        securityAttribute.setAttributeName(sysAttribute.getDescription());
        return securityAttribute;


    }

    private String permissionToCommaDelimitedString(Set<SecurityPermission> sysAuthorities) {
        if (CollectionUtils.isNotEmpty(sysAuthorities)) {
            List<String> codes = sysAuthorities.stream().map(SecurityPermission::getPermissionCode).toList();
            return StringUtils.collectionToCommaDelimitedString(codes);
        } else {
            return "";
        }
    }
}
