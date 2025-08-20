package com.ysmjjsy.goya.security.authorization.server.configuration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.ysmjjsy.goya.component.core.resolver.ResourceResolver;
import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.module.identity.configuration.properties.IdentityProperties;
import com.ysmjjsy.goya.security.authentication.configurer.OAuth2AuthenticationProviderConfigurer;
import com.ysmjjsy.goya.security.authentication.customizer.OAuth2AuthorizationServerConfigurerCustomizer;
import com.ysmjjsy.goya.security.authentication.customizer.OAuth2FormLoginConfigurerCustomizer;
import com.ysmjjsy.goya.security.authentication.response.OidcClientRegistrationResponseHandler;
import com.ysmjjsy.goya.security.authentication.utils.OAuth2ConfigurerUtils;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityResourceServerConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.properties.SecurityAuthorizationProperties;
import com.ysmjjsy.goya.security.core.enums.Certificate;
import com.ysmjjsy.goya.security.core.properties.SecurityEndpointProperties;
import com.ysmjjsy.goya.security.core.service.ClientDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:35
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@Import(SecurityRequestScanConfiguration.class)
public class SecurityAuthorizationServerConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Goya] |- Security [security authorization server] Configure.");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity httpSecurity,
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService,
            ClientDetailsService clientDetailsService,
            HttpCryptoProcessor httpCryptoProcessor,
            OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler,
            IdentityProperties identityProperties,
            OAuth2FormLoginConfigurerCustomizer oauth2FormLoginConfigurerCustomizer,
            SecurityResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer
    ) throws Exception {

        log.debug("[Herodotus] |- Bean [Authorization Server Security Filter Chain] Auto Configure.");

        SessionRegistry sessionRegistry = OAuth2ConfigurerUtils.getOptionalBean(httpSecurity, SessionRegistry.class);

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        // 仅拦截 OAuth2 Authorization Server 的相关 endpoint
        httpSecurity.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                // 不配置 oauth2ResourceServer 就不会启用BearerTokenAuthenticationFilter
                // 当前的版本 SAS(1.4.1) 环境下，oauth2ResourceServer 必须在 with(authorizationServerConfigurer 前面配置，否则会导致应用无法启动
                // 主要原因是 OAuth2AuthorizationServerConfigurer 默认 jwt 配置与 Opaqua 配置冲突。see：https://stackoverflow.com/questions/79336064/oidcuserinfoauthenticationprovider-doesnt-support-for-opaque-token-bearer-autho
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer)
                .with(authorizationServerConfigurer, new OAuth2AuthorizationServerConfigurerCustomizer(httpSecurity,
                        sessionRegistry, clientDetailsService, httpCryptoProcessor, oidcClientRegistrationResponseHandler))
                // 开启请求认证
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .formLogin(oauth2FormLoginConfigurerCustomizer)
                .with(new OAuth2AuthenticationProviderConfigurer(sessionRegistry, passwordEncoder, userDetailsService, identityProperties), (configurer) -> {
                });

        return httpSecurity.build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(SecurityAuthorizationProperties authorizationProperties) throws NoSuchAlgorithmException {

        SecurityAuthorizationProperties.Jwk jwk = authorizationProperties.getJwk();

        KeyPair keyPair = null;
        if (jwk.getCertificate() == Certificate.CUSTOM) {
            try {
                Resource[] resource = ResourceResolver.getResources(jwk.getJksKeyStore());
                if (ArrayUtils.isNotEmpty(resource)) {
                    KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource[0], jwk.getJksStorePassword().toCharArray());
                    keyPair = keyStoreKeyFactory.getKeyPair(jwk.getJksKeyAlias(), jwk.getJksKeyPassword().toCharArray());
                }
            } catch (IOException e) {
                log.error("[Goya] |- Read custom certificate under resource folder error!", e);
            }

        } else {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * jwt 解码
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(SecurityEndpointProperties endpointProperties) {
        return AuthorizationServerSettings.builder()
                .issuer(endpointProperties.getIssuerUri())
                .authorizationEndpoint(endpointProperties.getAuthorizationEndpoint())
                .pushedAuthorizationRequestEndpoint(endpointProperties.getPushedAuthorizationRequestEndpoint())
                .deviceAuthorizationEndpoint(endpointProperties.getDeviceAuthorizationEndpoint())
                .deviceVerificationEndpoint(endpointProperties.getDeviceVerificationEndpoint())
                .tokenEndpoint(endpointProperties.getAccessTokenEndpoint())
                .tokenIntrospectionEndpoint(endpointProperties.getTokenIntrospectionEndpoint())
                .tokenRevocationEndpoint(endpointProperties.getTokenRevocationEndpoint())
                .jwkSetEndpoint(endpointProperties.getJwkSetEndpoint())
                .oidcLogoutEndpoint(endpointProperties.getOidcLogoutEndpoint())
                .oidcUserInfoEndpoint(endpointProperties.getOidcUserInfoEndpoint())
                .oidcClientRegistrationEndpoint(endpointProperties.getOidcClientRegistrationEndpoint())
                .build();
    }
}
