package com.ysmjjsy.goya.security.authentication.client.configuration;

import com.ysmjjsy.goya.security.authentication.client.compliance.AccountStatusEventManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.DefaultAccountStatusEventManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.SecurityAccountStatusManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.listener.AccountAutoEnableListener;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.listener.AccountReleaseFromCacheListener;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.listener.AuthenticationFailureListener;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.listener.AuthenticationSuccessListener;
import com.ysmjjsy.goya.security.authentication.client.compliance.stamp.LockedUserDetailsStampManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.stamp.SignInFailureLimitedStampManager;
import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientApplicationService;
import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientComplianceService;
import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientDeviceService;
import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientScopeService;
import com.ysmjjsy.goya.security.authentication.client.engine.GoyaClientDetailsService;
import com.ysmjjsy.goya.security.authentication.client.listener.OidcClientRegistrationListener;
import com.ysmjjsy.goya.security.authentication.client.response.OAuth2DeviceVerificationResponseHandler;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.core.service.ClientDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 17:27
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EntityScan(basePackages = {
        "com.ysmjjsy.goya.security.authentication.client.domain.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.ysmjjsy.goya.security.authentication.client.domain.repository",
})
@ComponentScan(basePackages = {
        "com.ysmjjsy.goya.security.authentication.client.domain.service",
})
@EnableConfigurationProperties({SecurityAuthenticationProperties.class})
public class OAuth2ClientConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [oauth2 client] Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler(SecurityClientDeviceService securityClientDeviceService) {
        OAuth2DeviceVerificationResponseHandler handler = new OAuth2DeviceVerificationResponseHandler(securityClientDeviceService);
        log.trace("[Goya] |- Bean [OAuth2 Device Verification Response Handler] Configure.");
        return handler;
    }

    @Bean
    @ConditionalOnMissingBean
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
    @Order(Integer.MAX_VALUE - 1)
    @ConditionalOnBean(AccountStatusEventManager.class)
    public AccountStatusEventManager accountStatusEventManager() {
        DefaultAccountStatusEventManager manager = new DefaultAccountStatusEventManager();
        log.trace("[Goya] |- Bean [Goya Account Status Event Manager] Configure.");
        return manager;
    }

    @Bean
    public SecurityAccountStatusManager accountStatusManager(UserDetailsService userDetailsService, AccountStatusEventManager accountStatusChanger, LockedUserDetailsStampManager lockedUserDetailsStampManager) {
        SecurityAccountStatusManager manager = new SecurityAccountStatusManager(userDetailsService, accountStatusChanger, lockedUserDetailsStampManager);
        log.trace("[Goya] |- Bean [OAuth2 Account Status Manager] Auto Configure.");
        return manager;
    }

    @Bean
    public AccountAutoEnableListener accountLockStatusListener(RedisMessageListenerContainer redisMessageListenerContainer, SecurityAccountStatusManager accountStatusManager) {
        AccountAutoEnableListener listener = new AccountAutoEnableListener(redisMessageListenerContainer, accountStatusManager);
        log.trace("[Goya] |- Bean [OAuth2 Account Lock Status Listener] Auto Configure.");
        return listener;
    }

    @Bean
    public AccountReleaseFromCacheListener accountReleaseFromCacheListener(SecurityAccountStatusManager accountStatusManager) {
        AccountReleaseFromCacheListener listener = new AccountReleaseFromCacheListener(accountStatusManager);
        log.trace("[Goya] |- Bean [OAuth2 Account Release From Cache Listener] Auto Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureListener authenticationFailureListener(SignInFailureLimitedStampManager stampManager, SecurityAccountStatusManager accountLockService) {
        AuthenticationFailureListener listener = new AuthenticationFailureListener(stampManager, accountLockService);
        log.trace("[Goya] |- Bean [OAuth2 Authentication Failure Listener] Auto Configure.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessListener authenticationSuccessListener(SignInFailureLimitedStampManager stampManager, SecurityClientComplianceService securityClientComplianceService) {
        AuthenticationSuccessListener listener = new AuthenticationSuccessListener(stampManager, securityClientComplianceService);
        log.trace("[Goya] |- Bean [OAuth2 Authentication Success Listener] Auto Configure.");
        return listener;
    }

    @Bean
    public SignInFailureLimitedStampManager signInFailureLimitedStampManager(SecurityAuthenticationProperties authenticationProperties) {
        SignInFailureLimitedStampManager manager = new SignInFailureLimitedStampManager(authenticationProperties);
        log.trace("[Goya] |- Bean [SignIn Failure Limited Stamp Manager] Auto Configure.");
        return manager;
    }

}
