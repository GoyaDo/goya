package com.ysmjjsy.goya.security.ext.configuration;

import com.ysmjjsy.goya.module.jpa.annotation.EnableGoyaJpaRepositories;
import com.ysmjjsy.goya.security.core.service.ClientDetailsService;
import com.ysmjjsy.goya.security.ext.client.GoyaClientDetailsService;
import com.ysmjjsy.goya.security.ext.customizer.OAuth2AuthorizationDeviceServerConfigurerCustomizer;
import com.ysmjjsy.goya.security.ext.listener.AuthenticationSuccessSaveListener;
import com.ysmjjsy.goya.security.ext.listener.DeleteRegisteredClientListener;
import com.ysmjjsy.goya.security.ext.listener.OidcClientRegistrationListener;
import com.ysmjjsy.goya.security.ext.listener.SaveRegisteredClientListener;
import com.ysmjjsy.goya.security.ext.oauth2.JpaOAuth2AuthorizationConsentService;
import com.ysmjjsy.goya.security.ext.oauth2.JpaOAuth2AuthorizationService;
import com.ysmjjsy.goya.security.ext.oauth2.JpaRegisteredClientRepository;
import com.ysmjjsy.goya.security.ext.response.OAuth2DeviceVerificationResponseHandler;
import com.ysmjjsy.goya.security.ext.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:06
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EntityScan(basePackages = {
        "com.ysmjjsy.goya.security.ext.entity"
})
@EnableGoyaJpaRepositories(basePackages = {
        "com.ysmjjsy.goya.security.ext.repository"
})
@ComponentScan(basePackages = {
        "com.ysmjjsy.goya.security.ext.service",
})
public class SecurityExtAutoConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security ext] Configure.");
    }

    @Bean
    @Order
    public SecurityFilterChain securityExtFilterChain(
            HttpSecurity httpSecurity,
            OAuth2DeviceVerificationResponseHandler oAuth2DeviceVerificationResponseHandler
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Authorization Server Security Filter Chain] Auto Configure.");
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        // 仅拦截 OAuth2 Authorization Server 的相关 endpoint
        httpSecurity
                .with(authorizationServerConfigurer, new OAuth2AuthorizationDeviceServerConfigurerCustomizer(oAuth2DeviceVerificationResponseHandler));

        return httpSecurity.build();
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler(SecurityClientDeviceService securityClientDeviceService) {
        OAuth2DeviceVerificationResponseHandler handler = new OAuth2DeviceVerificationResponseHandler(securityClientDeviceService);
        log.trace("[Goya] |- Bean [OAuth2 Device Verification Response Handler] Configure.");
        return handler;
    }

    @Bean
    public OidcClientRegistrationListener oidcClientRegistrationListener(SecurityClientDeviceService securityClientDeviceService, SecurityClientScopeService securityClientScopeService) {
        OidcClientRegistrationListener listener = new OidcClientRegistrationListener(securityClientDeviceService, securityClientScopeService);
        log.trace("[Goya] |- Bean [Oidc Client Registration Listener] Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientDetailsService clientDetailsService(SecurityClientApplicationService securityClientApplicationService) {
        GoyaClientDetailsService goyaClientDetailsService = new GoyaClientDetailsService(securityClientApplicationService);
        log.debug("[Goya] |- Bean [Goya Client Details Service] Auto Configure.");
        return goyaClientDetailsService;
    }

    @Bean
    public AuthenticationSuccessSaveListener authenticationSuccessSaveListener(SecurityClientComplianceService securityClientComplianceService) {
        AuthenticationSuccessSaveListener listener = new AuthenticationSuccessSaveListener(securityClientComplianceService);
        log.trace("[Goya] |- Bean [Authentication Success Save Listener] Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public RegisteredClientRepository registeredClientRepository(SecurityRegisteredClientService securityRegisteredClientService, PasswordEncoder passwordEncoder) {
        JpaRegisteredClientRepository jpaRegisteredClientRepository = new JpaRegisteredClientRepository(securityRegisteredClientService, passwordEncoder);
        log.debug("[Goya] |- Bean [Jpa Registered Client Repository] Configure.");
        return jpaRegisteredClientRepository;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationService authorizationService(SecurityAuthorizationService securityAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationService jpaOAuth2AuthorizationService = new JpaOAuth2AuthorizationService(securityAuthorizationService, registeredClientRepository);
        log.debug("[Goya] |- Bean [Jpa OAuth2 Authorization Service] Configure.");
        return jpaOAuth2AuthorizationService;
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2AuthorizationConsentService authorizationConsentService(SecurityAuthorizationConsentService securityAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        JpaOAuth2AuthorizationConsentService jpaOAuth2AuthorizationConsentService = new JpaOAuth2AuthorizationConsentService(securityAuthorizationConsentService, registeredClientRepository);
        log.debug("[Goya] |- Bean [Jpa OAuth2 Authorization Consent Service] Configure.");
        return jpaOAuth2AuthorizationConsentService;
    }

    @Bean
    @ConditionalOnMissingBean
    public DeleteRegisteredClientListener deleteRegisteredClientListener(SecurityAuthorizationService securityAuthorizationService) {
        DeleteRegisteredClientListener listener = new DeleteRegisteredClientListener(securityAuthorizationService);
        log.debug("[Goya] |- Bean [Delete Registered Client Listener] Configure.");
        return listener;
    }


    @Bean
    @ConditionalOnMissingBean
    public SaveRegisteredClientListener saveRegisteredClientListener(RegisteredClientRepository registeredClientRepository) {
        SaveRegisteredClientListener listener = new SaveRegisteredClientListener(registeredClientRepository);
        log.trace("[Goya] |- Bean [Save Registered Client Listener] Configure.");
        return listener;
    }
}
