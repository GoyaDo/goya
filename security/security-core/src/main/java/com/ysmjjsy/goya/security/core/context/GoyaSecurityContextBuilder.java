package com.ysmjjsy.goya.security.core.context;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import com.ysmjjsy.goya.component.dto.enums.Architecture;
import com.ysmjjsy.goya.security.core.properties.SecurityEndpointProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 21:45
 */
public class GoyaSecurityContextBuilder {

    private SecurityEndpointProperties securityEndpointProperties;


    public static GoyaSecurityContextBuilder builder() {
        return new GoyaSecurityContextBuilder();
    }

    public GoyaSecurityContextBuilder endpointProperties(SecurityEndpointProperties securityEndpointProperties) {
        this.securityEndpointProperties = securityEndpointProperties;
        return this;
    }

    public GoyaSecurityContext build() {
        GoyaSecurityContext holder = GoyaSecurityContext.getInstance();
        setProperties(holder);
        return holder;
    }


    private void setProperties(GoyaSecurityContext goyaSecurityContext) {
        if (GoyaContextHolder.getInstance().getArchitecture() == Architecture.MONOCOQUE) {
            String issuerUri = securityEndpointProperties.getIssuerUri();
            goyaSecurityContext.setGatewayServiceUri(issuerUri);
            goyaSecurityContext.setAuthServiceUri(issuerUri);
        } else {
            goyaSecurityContext.setAuthServiceName(securityEndpointProperties.getAuthServiceName());
            goyaSecurityContext.setGatewayServiceUri(securityEndpointProperties.getGatewayServiceUri());
            goyaSecurityContext.setAuthorizationUri(securityEndpointProperties.getAuthorizationUri());
        }

        goyaSecurityContext.setAuthorizationUri(securityEndpointProperties.getAuthorizationUri());
        goyaSecurityContext.setAuthorizationEndpoint(securityEndpointProperties.getAuthorizationEndpoint());
        goyaSecurityContext.setPushedAuthorizationRequestUri(securityEndpointProperties.getPushedAuthorizationRequestUri());
        goyaSecurityContext.setPushedAuthorizationRequestEndpoint(securityEndpointProperties.getPushedAuthorizationRequestEndpoint());
        goyaSecurityContext.setAccessTokenUri(securityEndpointProperties.getAccessTokenUri());
        goyaSecurityContext.setAccessTokenEndpoint(securityEndpointProperties.getAccessTokenEndpoint());
        goyaSecurityContext.setJwkSetUri(securityEndpointProperties.getJwkSetUri());
        goyaSecurityContext.setJwkSetEndpoint(securityEndpointProperties.getJwkSetEndpoint());
        goyaSecurityContext.setTokenRevocationUri(securityEndpointProperties.getTokenRevocationUri());
        goyaSecurityContext.setTokenRevocationEndpoint(securityEndpointProperties.getTokenRevocationEndpoint());
        goyaSecurityContext.setTokenIntrospectionUri(securityEndpointProperties.getTokenIntrospectionUri());
        goyaSecurityContext.setTokenIntrospectionEndpoint(securityEndpointProperties.getTokenIntrospectionEndpoint());
        goyaSecurityContext.setDeviceAuthorizationUri(securityEndpointProperties.getDeviceAuthorizationUri());
        goyaSecurityContext.setDeviceAuthorizationEndpoint(securityEndpointProperties.getDeviceAuthorizationEndpoint());
        goyaSecurityContext.setDeviceVerificationUri(securityEndpointProperties.getDeviceVerificationUri());
        goyaSecurityContext.setDeviceVerificationEndpoint(securityEndpointProperties.getDeviceVerificationEndpoint());
        goyaSecurityContext.setOidcClientRegistrationUri(securityEndpointProperties.getOidcClientRegistrationUri());
        goyaSecurityContext.setOidcClientRegistrationEndpoint(securityEndpointProperties.getOidcClientRegistrationEndpoint());
        goyaSecurityContext.setOidcLogoutUri(securityEndpointProperties.getOidcLogoutUri());
        goyaSecurityContext.setOidcLogoutEndpoint(securityEndpointProperties.getOidcLogoutEndpoint());
        goyaSecurityContext.setOidcUserInfoUri(securityEndpointProperties.getOidcUserInfoUri());
        goyaSecurityContext.setOidcUserInfoEndpoint(securityEndpointProperties.getOidcUserInfoEndpoint());
        goyaSecurityContext.setIssuerUri(securityEndpointProperties.getIssuerUri());
    }

}
