package com.ysmjjsy.goya.component.web.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.util.WebUtils;

/**
 * <p>Description: Cookie 操作工具类 </p>
 *
 * @author goya
 * @since 2023/9/2 16:38
 */
public class CookieUtils extends HttpUtils {



    /**
     * 获取 Cookie 对象
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return {@link Cookie} 对象
     */
    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        return WebUtils.getCookie(httpServletRequest, name);
    }

    /**
     * 获取 Cookie 属性值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String get(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = getCookie(httpServletRequest, name);
        return ObjectUtils.isNotEmpty(cookie) ? cookie.getValue() : null;
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromCookieHeader(HttpServletRequest httpServletRequest, String name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return get(cookie, name);
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromCookieHeader(ServerHttpRequest serverHttpRequest, String name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return get(cookie, name);
    }

    /**
     * 获取 Cookie 请求头中，某个属性的值
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @param name             Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getFromCookieHeader(HttpInputMessage httpInputMessage, String name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return get(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param name               Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromCookieHeader(HttpServletRequest httpServletRequest, String... name) {
        String cookie = HeaderUtils.getCookie(httpServletRequest);
        return getAny(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @param name              Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromCookieHeader(ServerHttpRequest serverHttpRequest, String... name) {
        String cookie = HeaderUtils.getCookie(serverHttpRequest);
        return getAny(cookie, name);
    }

    /**
     * 从 Cookie 请求头中，找到给定任意给定属性的值
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @param name             Cookie中的属性名
     * @return 如果 Cookie 存在属性名就返回对应的值，如果不存在则返回null
     */
    public static String getAnyFromCookieHeader(HttpInputMessage httpInputMessage, String... name) {
        String cookie = HeaderUtils.getCookie(httpInputMessage);
        return getAny(cookie, name);
    }

    /**
     * 清除 某个指定的cookie
     *
     * @param response HttpServletResponse
     * @param key      cookie key
     */
    public static void remove(HttpServletResponse response, String key) {
        set(response, key, null, 0);
    }

    /**
     * 设置cookie
     *
     * @param response        HttpServletResponse
     * @param name            cookie name
     * @param value           cookie value
     * @param maxAgeInSeconds maxage
     */
    public static void set(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
