package com.ysmjjsy.goya.component.web.utils;

import com.ysmjjsy.goya.component.pojo.constants.SymbolConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Description: Http 通用工具类 </p>
 *
 * @author goya
 * @since 2024/2/5 21:50
 */
public class HttpUtils {

    /**
     * 解析 Cookie 头的值解析为 Map
     *
     * @param cookie Cookie 头的值
     * @return Cookie 键值对。
     */
    private static Map<String, String> rawCookieToMap(String cookie) {
        if (StringUtils.isNotBlank(cookie)) {
            return Stream.of(cookie.split(SymbolConstants.SEMICOLON_AND_SPACE))
                    .map(pair -> pair.split(SymbolConstants.EQUAL))
                    .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * 获取多个 Cookie 请求头中的属性值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值的集合
     */
    public static List<String> get(String cookie, String... name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        return Stream.of(name).map(cookies::get).toList();
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值的集合
     */
    public static String getAny(String cookie, String... name) {
        List<String> result = get(cookie, name);
        return CollectionUtils.isNotEmpty(result) ? result.getFirst() : null;
    }

    /**
     * 获取 Cookie 请求头中的属性值
     *
     * @param cookie Cookie 请求头值
     * @param name   Cookie中的属性名
     * @return cookie 中属性值
     */
    public static String get(String cookie, String name) {
        Map<String, String> cookies = rawCookieToMap(cookie);
        return cookies.get(name);
    }

    /**
     * 判断是否为 GET 请求
     *
     * @param method {@link HttpMethod}
     * @return true 是 GET 请求，false 不是 GET 请求
     */
    public static boolean isGetRequest(HttpMethod method) {
        return method == HttpMethod.GET;
    }

    /**
     * 判断是否为 POST 类型 请求
     *
     * @param method      {@link HttpMethod}
     * @param contentType 内容类型
     * @return true 是 POST 请求，false 不是 POST 请求
     */
    public static Boolean isPostRequest(HttpMethod method, String contentType) {
        return (method == HttpMethod.POST || method == HttpMethod.PUT) && (MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_VALUE.equals(contentType));
    }
}
