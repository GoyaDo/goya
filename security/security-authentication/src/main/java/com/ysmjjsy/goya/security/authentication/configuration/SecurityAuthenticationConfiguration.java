package com.ysmjjsy.goya.security.authentication.configuration;

import com.ysmjjsy.goya.module.identity.manage.UserStatusManager;
import com.ysmjjsy.goya.module.identity.stamp.SignInFailureLimitedStampManager;
import com.ysmjjsy.goya.security.authentication.customizer.GoyaJwtTokenCustomizer;
import com.ysmjjsy.goya.security.authentication.customizer.GoyaOpaqueTokenCustomizer;
import com.ysmjjsy.goya.security.authentication.customizer.OAuth2FormLoginConfigurerCustomizer;
import com.ysmjjsy.goya.security.authentication.listener.AuthenticationFailureListener;
import com.ysmjjsy.goya.security.authentication.listener.AuthenticationSuccessListener;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.authentication.response.OidcClientRegistrationResponseHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 17:30
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
@EnableConfigurationProperties(SecurityAuthenticationProperties.class)
public class SecurityAuthenticationConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security authentication] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler(RegisteredClientRepository registeredClientRepository) {
        OidcClientRegistrationResponseHandler handler = new OidcClientRegistrationResponseHandler(registeredClientRepository);
        log.trace("[Goya] |- Bean [Oidc Client Registration Response Handler] Configure.");
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2FormLoginConfigurerCustomizer oauth2FormLoginConfigurerCustomer(SecurityAuthenticationProperties authenticationProperties) {
        OAuth2FormLoginConfigurerCustomizer configurer = new OAuth2FormLoginConfigurerCustomizer(authenticationProperties);
        log.trace("[Goya] |- Bean [OAuth2 FormLogin Configurer Customer] Configure.");
        return configurer;
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        GoyaJwtTokenCustomizer customizer = new GoyaJwtTokenCustomizer();
        log.trace("[Goya] |- Bean [OAuth2 Jwt Token Customizer] Configure.");
        return customizer;
    }

    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> opaqueTokenCustomizer() {
        GoyaOpaqueTokenCustomizer customizer = new GoyaOpaqueTokenCustomizer();
        log.trace("[Goya] |- Bean [OAuth2 Opaque Token Customizer] Configure.");
        return customizer;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureListener authenticationFailureListener(SignInFailureLimitedStampManager stampManager, UserStatusManager userStatusManager) {
        AuthenticationFailureListener listener = new AuthenticationFailureListener(stampManager, userStatusManager);
        log.trace("[Goya] |- Bean [OAuth2 Authentication Failure Listener] Auto Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessListener authenticationSuccessListener(SignInFailureLimitedStampManager stampManager) {
        AuthenticationSuccessListener listener = new AuthenticationSuccessListener(stampManager);
        log.trace("[Goya] |- Bean [OAuth2 Authentication Success Listener] Auto Configure.");
        return listener;
    }
}
