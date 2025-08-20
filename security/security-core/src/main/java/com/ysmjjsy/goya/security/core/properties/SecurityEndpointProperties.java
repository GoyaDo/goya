package com.ysmjjsy.goya.security.core.properties;

import com.ysmjjsy.goya.component.core.utils.WellFormedUtils;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 21:31
 */
@Data
@ConfigurationProperties(prefix = SecurityConstants.PROPERTY_PREFIX_SECURITY_ENDPOINT)
public class SecurityEndpointProperties{

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

    public String getAuthorizationUri() {
        return WellFormedUtils.sasUri(authorizationUri, getAuthorizationEndpoint(), getIssuerUri());
    }

    public String getPushedAuthorizationRequestUri() {
        return WellFormedUtils.sasUri(pushedAuthorizationRequestUri, getPushedAuthorizationRequestEndpoint(), getIssuerUri());
    }

    public String getAccessTokenUri() {
        return WellFormedUtils.sasUri(accessTokenUri, getAccessTokenEndpoint(), getIssuerUri());
    }

    public String getJwkSetUri() {
        return WellFormedUtils.sasUri(jwkSetUri, getJwkSetEndpoint(), getIssuerUri());
    }

    public String getTokenRevocationUri() {
        return WellFormedUtils.sasUri(tokenRevocationUri, getTokenRevocationEndpoint(), getIssuerUri());
    }

    public String getTokenIntrospectionUri() {
        return WellFormedUtils.sasUri(tokenIntrospectionUri, getTokenIntrospectionEndpoint(), getIssuerUri());
    }

    public String getDeviceAuthorizationUri() {
        return WellFormedUtils.sasUri(deviceAuthorizationUri, getDeviceAuthorizationEndpoint(), getIssuerUri());
    }


    public String getDeviceVerificationUri() {
        return WellFormedUtils.sasUri(deviceVerificationUri, getDeviceVerificationEndpoint(), getIssuerUri());
    }


    public String getOidcClientRegistrationUri() {
        return WellFormedUtils.sasUri(oidcClientRegistrationUri, getOidcClientRegistrationEndpoint(), getIssuerUri());
    }

    public String getOidcLogoutUri() {
        return WellFormedUtils.sasUri(oidcLogoutUri, getOidcLogoutEndpoint(), getIssuerUri());
    }

    public String getOidcUserInfoUri() {
        return WellFormedUtils.sasUri(oidcUserInfoUri, getOidcUserInfoEndpoint(), getIssuerUri());
    }
}
