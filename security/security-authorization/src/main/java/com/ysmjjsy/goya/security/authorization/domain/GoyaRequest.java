package com.ysmjjsy.goya.security.authorization.domain;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>Description: 自定义 AntPathRequestMatcher </p>
 * <p>
 * 基于 AntPathRequestMatcher 代码，扩展了一些方法，解决原有 AntPathRequestMatcher 使用不方便问题。
 *
 * @author goya
 * @since 2025/7/8 22:43
 */
@Data
public final class GoyaRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 2013424123660343193L;

    private String pattern;

    private String httpMethod;

    private boolean hasWildcard;

    public GoyaRequest() {
    }

    /**
     * Creates a matcher with the specific pattern which will match all HTTP methods in a
     * case sensitive manner.
     *
     * @param pattern the ant pattern to use for matching
     */
    public GoyaRequest(String pattern) {
        this(pattern, null);
    }

    /**
     * Creates a matcher with the supplied pattern which will match the specified Http
     * method
     *
     * @param pattern    the ant pattern to use for matching
     * @param httpMethod the HTTP method. The {@code matches} method will return false if
     *                   the incoming request doesn't have the same method.
     */
    public GoyaRequest(String pattern, String httpMethod) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.pattern = pattern;
        this.hasWildcard = containSpecialCharacters(pattern);
        this.httpMethod = checkHttpMethod(httpMethod);
    }

    private String checkHttpMethod(String method) {
        if (StringUtils.isNotBlank(method)) {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            if (ObjectUtils.isNotEmpty(httpMethod)) {
                return httpMethod.name();
            }
        }
        return null;
    }

    private boolean containSpecialCharacters(String source) {
        if (StringUtils.isNotBlank(source)) {
            return StringUtils.containsAny(source, new String[]{"*", "?", "{"});
        }
        return false;
    }
}