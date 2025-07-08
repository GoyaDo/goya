package com.ysmjjsy.goya.security.core.context;

import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 21:40
 */
@Data
public class GoyaSecurityContext {

    /**
     * 认证中心服务名称
     */
    private String authServiceName;

    /**
     * 统一认证中心服务地址
     */
    private String authServiceUri;

    /**
     * 统一网关服务地址。可以是IP+端口，可以是域名
     */
    private String gatewayServiceUri;

    /**
     * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize uri 地址，可修改为自定义地址
     */
    private String authorizationUri;

    /**
     * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize端点地址，可修改为自定义地址
     */
    private String authorizationEndpoint = SecurityConstants.AUTHORIZATION_ENDPOINT;
    /**
     * OAuth2 Pushed Authorization Requests 模式认证端点，/oauth2/par uri 地址，可修改为自定义地址
     */
    private String pushedAuthorizationRequestUri;
    /**
     * OAuth2 Pushed Authorization Requests 模式认证端点，/oauth2/authorize端点地址，可修改为自定义地址
     */
    private String pushedAuthorizationRequestEndpoint = SecurityConstants.PUSHED_AUTHORIZATION_REQUEST_ENDPOINT;
    /**
     * OAuth2 /oauth2/token 申请 Token uri 地址，可修改为自定义地址
     */
    private String accessTokenUri;
    /**
     * OAuth2 /oauth2/token 申请 Token 端点地址，可修改为自定义地址
     */
    private String accessTokenEndpoint = SecurityConstants.TOKEN_ENDPOINT;
    /**
     * OAuth2 /oauth2/jwks uri 地址，可修改为自定义地址
     */
    private String jwkSetUri;
    /**
     * OAuth2 /oauth2/jwks 端点地址，可修改为自定义地址
     */
    private String jwkSetEndpoint = SecurityConstants.JWK_SET_ENDPOINT;
    /**
     * OAuth2 /oauth2/revoke 撤销 Token uri 地址，可修改为自定义地址
     */
    private String tokenRevocationUri;
    /**
     * OAuth2 /oauth2/revoke 撤销 Token 端点地址，可修改为自定义地址
     */
    private String tokenRevocationEndpoint = SecurityConstants.TOKEN_REVOCATION_ENDPOINT;
    /**
     * OAuth2 /oauth2/introspect 查看 Token uri地址，可修改为自定义地址
     */
    private String tokenIntrospectionUri;
    /**
     * OAuth2 /oauth2/introspect 查看 Token 端点地址，可修改为自定义地址
     */
    private String tokenIntrospectionEndpoint = SecurityConstants.TOKEN_INTROSPECTION_ENDPOINT;
    /**
     * OAuth2 /oauth2/device_authorization 设备授权认证 uri地址，可修改为自定义地址
     */
    private String deviceAuthorizationUri;
    /**
     * OAuth2 /oauth2/device_authorization 设备授权认证端点地址，可修改为自定义地址
     */
    private String deviceAuthorizationEndpoint = SecurityConstants.DEVICE_AUTHORIZATION_ENDPOINT;
    /**
     * OAuth2 /oauth2/device_verification 设备授权校验 uri地址，可修改为自定义地址
     */
    private String deviceVerificationUri;
    /**
     * OAuth2 /oauth2/device_verification 设备授权校验端点地址，可修改为自定义地址
     */
    private String deviceVerificationEndpoint = SecurityConstants.DEVICE_VERIFICATION_ENDPOINT;
    /**
     * OAuth2 OIDC /connect/register uri 地址，可修改为自定义地址
     */
    private String oidcClientRegistrationUri;
    /**
     * OAuth2 OIDC /connect/register 端点地址，可修改为自定义地址
     */
    private String oidcClientRegistrationEndpoint = SecurityConstants.OIDC_CLIENT_REGISTRATION_ENDPOINT;
    /**
     * OAuth2 OIDC /connect/logout uri 地址，可修改为自定义地址
     */
    private String oidcLogoutUri;
    /**
     * OAuth2 OIDC /connect/logout 端点地址，可修改为自定义地址
     */
    private String oidcLogoutEndpoint = SecurityConstants.OIDC_LOGOUT_ENDPOINT;
    /**
     * OAuth2 OIDC /userinfo uri 地址，可修改为自定义地址
     */
    private String oidcUserInfoUri;
    /**
     * OAuth2 OIDC /userinfo 端点地址，可修改为自定义地址
     */
    private String oidcUserInfoEndpoint = SecurityConstants.OIDC_USER_INFO_ENDPOINT;
    /**
     * Spring Authorization Server Issuer Url
     */
    private String issuerUri;

    private static volatile GoyaSecurityContext instance;

    public static GoyaSecurityContext getInstance() {
        if (ObjectUtils.isEmpty(instance)) {
            synchronized (GoyaSecurityContext.class) {
                if (ObjectUtils.isEmpty(instance)) {
                    instance = new GoyaSecurityContext();
                }
            }
        }

        return instance;
    }
}
