package com.ysmjjsy.goya.security.authorization.configuration;

import com.ysmjjsy.goya.module.identity.resolver.BearerTokenResolver;
import com.ysmjjsy.goya.security.authorization.annotation.ConditionalOnUseJwtToken;
import com.ysmjjsy.goya.security.authorization.annotation.ConditionalOnUseOpaqueToken;
import com.ysmjjsy.goya.security.authorization.auditing.SecurityAuditorAware;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityAuthorizeHttpRequestsConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityResourceServerConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.introspector.SecurityOpaqueTokenIntrospector;
import com.ysmjjsy.goya.security.authorization.processor.SecurityAuthorizationManager;
import com.ysmjjsy.goya.security.authorization.processor.SecurityMatcherConfigurer;
import com.ysmjjsy.goya.security.authorization.processor.SecurityMetadataSourceStorage;
import com.ysmjjsy.goya.security.authorization.properties.SecurityAuthorizationProperties;
import com.ysmjjsy.goya.security.authorization.resolver.GoyaServletJwtTokenResolver;
import com.ysmjjsy.goya.security.authorization.resolver.GoyaServletOpaqueTokenResolver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:43
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityAuthorizationProperties.class)
public class SecurityAuthorizationConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security authorization] Configure.");
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        SecurityAuditorAware securityAuditorAware = new SecurityAuditorAware();
        log.debug("[Goya] |- Bean [Security Auditor Aware] Auto Configure.");
        return securityAuditorAware;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMetadataSourceStorage securityMetadataSourceStorage() {
        SecurityMetadataSourceStorage securityMetadataSourceStorage = new SecurityMetadataSourceStorage();
        log.trace("[Goya] |- Bean [Security Metadata Source Storage] Auto Configure.");
        return securityMetadataSourceStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityAuthorizationManager securityAuthorizationManager(SecurityMetadataSourceStorage securityMetadataSourceStorage, SecurityMatcherConfigurer securityMatcherConfigurer) {
        SecurityAuthorizationManager securityAuthorizationManager = new SecurityAuthorizationManager(securityMetadataSourceStorage, securityMatcherConfigurer);
        log.trace("[Goya] |- Bean [Authorization Manager] Auto Configure.");
        return securityAuthorizationManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityMatcherConfigurer securityMatcherConfigurer(SecurityAuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        SecurityMatcherConfigurer securityMatcherConfigurer = new SecurityMatcherConfigurer(authorizationProperties, resourceUrlProvider);
        log.trace("[Goya] |- Bean [Security Metadata Configurer] Auto Configure.");
        return securityMatcherConfigurer;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public SecurityResourceServerConfigurerCustomer securityResourceServerConfigurerCustomer(JwtDecoder jwtDecoder,
                                                                                             SecurityAuthorizationProperties securityAuthorizationProperties,
                                                                                             OAuth2ResourceServerProperties resourceServerProperties) {
        SecurityResourceServerConfigurerCustomer securityResourceServerConfigurerCustomer = new SecurityResourceServerConfigurerCustomer(jwtDecoder, securityAuthorizationProperties, resourceServerProperties);
        log.trace("[Goya] |- Bean [OAuth2 Resource Server Configurer Customer] Auto Configure.");
        return securityResourceServerConfigurerCustomer;
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityAuthorizeHttpRequestsConfigurerCustomer authorizeHttpRequestsConfigurerCustomer(SecurityMatcherConfigurer securityMatcherConfigurer, SecurityAuthorizationManager securityAuthorizationManager) {
        SecurityAuthorizeHttpRequestsConfigurerCustomer authorizeHttpRequestsConfigurerCustomer = new SecurityAuthorizeHttpRequestsConfigurerCustomer(securityMatcherConfigurer, securityAuthorizationManager);
        log.trace("[Goya] |- Bean [Authorize Http Requests Configurer Customer] Auto Configure.");
        return authorizeHttpRequestsConfigurerCustomer;
    }
    
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(OpaqueTokenIntrospector.class)
    @ConditionalOnUseOpaqueToken
    static class OAuth2OpaqueTokenConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public OpaqueTokenIntrospector goyaServletOpaqueTokenIntrospector(OAuth2ResourceServerProperties resourceServerProperties) {
            SecurityOpaqueTokenIntrospector introspector = new SecurityOpaqueTokenIntrospector(resourceServerProperties);
            log.trace("[Goya] |- Bean [Goya Servlet Opaque Token Introspector] Configure.");
            return introspector;
        }

        @Bean
        @ConditionalOnMissingBean
        public BearerTokenResolver opaqueBearerTokenResolver(OpaqueTokenIntrospector OpaqueTokenIntrospector) {
            GoyaServletOpaqueTokenResolver resolver = new GoyaServletOpaqueTokenResolver(OpaqueTokenIntrospector);
            log.trace("[Goya] |- Bean [Goya Servlet Opaque Token Resolver] Configure.");
            return resolver;
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(JwtDecoder.class)
    @ConditionalOnUseJwtToken
    static class OAuth2JwtTokenConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public BearerTokenResolver jwtBearerTokenResolver(JwtDecoder jwtDecoder) {
            GoyaServletJwtTokenResolver resolver = new GoyaServletJwtTokenResolver(jwtDecoder);
            log.trace("[Goya] |- Bean [Goya Servlet JWT Token Resolver] Configure.");
            return resolver;
        }
    }

}
