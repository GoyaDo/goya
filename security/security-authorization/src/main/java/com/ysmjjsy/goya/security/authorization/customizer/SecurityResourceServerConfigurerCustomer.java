package com.ysmjjsy.goya.security.authorization.customizer;

import com.ysmjjsy.goya.component.common.pojo.enums.Target;
import com.ysmjjsy.goya.security.authorization.converter.GoyaJwtAuthenticationConverter;
import com.ysmjjsy.goya.security.authorization.introspector.SecurityOpaqueTokenIntrospector;
import com.ysmjjsy.goya.security.authorization.properties.SecurityAuthorizationProperties;
import com.ysmjjsy.goya.security.authorization.response.GoyaAccessDeniedHandler;
import com.ysmjjsy.goya.security.authorization.response.GoyaAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:43
 */
@Slf4j
public class SecurityResourceServerConfigurerCustomer implements Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> {

    private final JwtDecoder jwtDecoder;
    private final OpaqueTokenIntrospector opaqueTokenIntrospector;
    private final SecurityAuthorizationProperties securityAuthorizationProperties;

    public SecurityResourceServerConfigurerCustomer(JwtDecoder jwtDecoder,
                                                    SecurityAuthorizationProperties securityAuthorizationProperties,
                                                    OAuth2ResourceServerProperties resourceServerProperties) {
        this.jwtDecoder = jwtDecoder;
        this.opaqueTokenIntrospector = new SecurityOpaqueTokenIntrospector(resourceServerProperties);
        this.securityAuthorizationProperties = securityAuthorizationProperties;
    }

    private boolean isRemoteValidate() {
        return this.securityAuthorizationProperties.getValidate() == Target.REMOTE;
    }

    @Override
    public void customize(OAuth2ResourceServerConfigurer<HttpSecurity> configurer) {
        if (isRemoteValidate()) {
            configurer
                    .opaqueToken(opaque -> opaque.introspector(opaqueTokenIntrospector));
        } else {
            configurer
                    .jwt(jwt -> jwt.decoder(this.jwtDecoder).jwtAuthenticationConverter(new GoyaJwtAuthenticationConverter()))
                    .bearerTokenResolver(new DefaultBearerTokenResolver());
        }

        configurer
                .accessDeniedHandler(new GoyaAccessDeniedHandler())
                .authenticationEntryPoint(new GoyaAuthenticationEntryPoint());
    }
}
