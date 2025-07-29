package com.ysmjjsy.goya.component.pojo.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Description: URL 路径类别 </p>
 *
 * @author goya
 * @since 2022/3/9 19:06
 */
public enum UrlCategory {

    /**
     * 含有通配符，含有 "*" 或 "?"
     */
    WILDCARD,
    /**
     * 含有占位符，含有 "{" 和 " } "
     */
    PLACEHOLDER,
    /**
     * 不含有任何特殊字符的完整路径
     */
    FULL_PATH;

    public static UrlCategory getCategory(String url) {

        if (StringUtils.containsAny(url, new String[]{"*", "?"})) {
            return UrlCategory.WILDCARD;
        }

        if (StringUtils.contains(url, "{")) {
            return UrlCategory.PLACEHOLDER;
        }

        return UrlCategory.FULL_PATH;
    }
}
