package com.ysmjjsy.goya.security.authorization.constants;

import com.google.common.collect.Lists;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;

import java.util.List;

/**
 * <p>Description: Web配置常量 </p>
 *
 * @author goya
 * @since 2025/7/8 23:41
 */
public class SecurityResources {

    public static final List<String> DEFAULT_IGNORED_STATIC_RESOURCES = Lists.newArrayList(
            "/error/**",
            "/plugins/**",
            "/goya/**",
            "/static/**",
            "/webjars/**",
            "/assets/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/openapi.json",
            "/favicon.ico");
    public static final List<String> DEFAULT_PERMIT_ALL_RESOURCES = Lists.newArrayList("/open/**", "/stomp/ws", "/oauth2/sign-out", "/login*");

    public static final List<String> DEFAULT_HAS_AUTHENTICATED_RESOURCES = Lists.newArrayList("/engine-rest/**", SecurityConstants.DEVICE_VERIFICATION_SUCCESS_URI, SecurityConstants.AUTHORIZATION_CONSENT_URI);
}
