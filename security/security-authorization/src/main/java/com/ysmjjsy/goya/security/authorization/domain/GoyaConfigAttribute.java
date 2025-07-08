package com.ysmjjsy.goya.security.authorization.domain;

import lombok.Data;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 自定义SecurityConfig </p>
 * <p>
 * 自定义SecurityConfig，主要为了构建无参数构造函数，以解决序列化出错问题
 *
 * @author goya
 * @since 2025/7/8 22:43
 */
@Data
public class GoyaConfigAttribute implements ConfigAttribute {

    @Serial
    private static final long serialVersionUID = -947713088934205513L;

    private String attribute;

    public GoyaConfigAttribute() {
    }

    public GoyaConfigAttribute(String config) {
        Assert.hasText(config, "You must provide a configuration attribute");
        this.attribute = config;
    }

    public static GoyaConfigAttribute create(String attribute) {
        Assert.notNull(attribute, "You must supply an array of attribute names");
        return new GoyaConfigAttribute(attribute.trim());
    }

    public static List<ConfigAttribute> createListFromCommaDelimitedString(String access) {
        return createList(StringUtils.commaDelimitedListToStringArray(access));
    }

    public static List<ConfigAttribute> createList(String... attributeNames) {
        Assert.notNull(attributeNames, "You must supply an array of attribute names");
        List<ConfigAttribute> attributes = new ArrayList<>(attributeNames.length);
        for (String attribute : attributeNames) {
            attributes.add(new GoyaConfigAttribute(attribute.trim()));
        }
        return attributes;
    }
}
