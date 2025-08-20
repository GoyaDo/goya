package com.ysmjjsy.goya.security.authentication.customizer;

import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.security.authentication.consumer.OAuth2AuthorizationCodeAuthenticationProviderConsumer;
import com.ysmjjsy.goya.security.authentication.consumer.OAuth2ClientCredentialsAuthenticationProviderConsumer;
import com.ysmjjsy.goya.security.authentication.consumer.OidcClientRegistrationAuthenticationProviderConsumer;
import com.ysmjjsy.goya.security.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.ysmjjsy.goya.security.authentication.provider.OAuth2SocialCredentialsAuthenticationConverter;
import com.ysmjjsy.goya.security.authentication.response.OAuth2AccessTokenResponseHandler;
import com.ysmjjsy.goya.security.core.response.OAuth2AuthenticationFailureResponseHandler;
import com.ysmjjsy.goya.security.authentication.response.OidcClientRegistrationResponseHandler;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.service.ClientDetailsService;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2DeviceCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;

import java.util.Arrays;

/**
 * <p>Description: OAuth2AuthorizationServerConfigurer 自定义配置 </p>
 *
 * @author goya
 * @since 2024/5/20 12:03
 */
public class OAuth2AuthorizationServerConfigurerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    private final HttpSecurity httpSecurity;
    private final SessionRegistry sessionRegistry;
    private final ClientDetailsService clientDetailsService;
    private final HttpCryptoProcessor httpCryptoProcessor;
    private final OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler;
    private final OAuth2AuthenticationFailureResponseHandler oauth2AuthenticationFailureResponseHandler;

    public OAuth2AuthorizationServerConfigurerCustomizer(HttpSecurity httpSecurity, SessionRegistry sessionRegistry, ClientDetailsService clientDetailsService,
                                                         HttpCryptoProcessor httpCryptoProcessor,
                                                         OidcClientRegistrationResponseHandler oidcClientRegistrationResponseHandler) {
        this.httpSecurity = httpSecurity;
        this.sessionRegistry = sessionRegistry;
        this.clientDetailsService = clientDetailsService;
        this.httpCryptoProcessor = httpCryptoProcessor;
        this.oidcClientRegistrationResponseHandler = oidcClientRegistrationResponseHandler;
        this.oauth2AuthenticationFailureResponseHandler = new OAuth2AuthenticationFailureResponseHandler();
    }

    @Override
    public void customize(OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer) {

        oauth2AuthorizationServerConfigurer.clientAuthentication(endpoint -> {
            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
            endpoint.authenticationProviders(new OAuth2ClientCredentialsAuthenticationProviderConsumer(httpSecurity, clientDetailsService));
        });
        oauth2AuthorizationServerConfigurer.authorizationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
            endpoint.consentPage(SecurityConstants.AUTHORIZATION_CONSENT_URI);
        });
        oauth2AuthorizationServerConfigurer.deviceAuthorizationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
            endpoint.verificationUri(SecurityConstants.DEVICE_ACTIVATION_URI);
        });
        oauth2AuthorizationServerConfigurer.tokenEndpoint(endpoint -> {
            AuthenticationConverter authenticationConverter = new DelegatingAuthenticationConverter(
                    Arrays.asList(
                            new OAuth2AuthorizationCodeAuthenticationConverter(),
                            new OAuth2RefreshTokenAuthenticationConverter(),
                            new OAuth2ClientCredentialsAuthenticationConverter(),
                            new OAuth2DeviceCodeAuthenticationConverter(),
                            new OAuth2ResourceOwnerPasswordAuthenticationConverter(httpCryptoProcessor),
                            new OAuth2SocialCredentialsAuthenticationConverter(httpCryptoProcessor)));
            endpoint.accessTokenRequestConverter(authenticationConverter);
            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
            endpoint.accessTokenResponseHandler(new OAuth2AccessTokenResponseHandler(httpCryptoProcessor));
            endpoint.authenticationProviders(new OAuth2AuthorizationCodeAuthenticationProviderConsumer(httpSecurity, sessionRegistry));
        });
        oauth2AuthorizationServerConfigurer.tokenIntrospectionEndpoint(endpoint -> endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler));
        oauth2AuthorizationServerConfigurer.tokenRevocationEndpoint(endpoint -> endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler));
        oauth2AuthorizationServerConfigurer.oidc(oidc -> {
            oidc.clientRegistrationEndpoint(endpoint -> {
                endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
                endpoint.authenticationProviders(new OidcClientRegistrationAuthenticationProviderConsumer());
                endpoint.clientRegistrationResponseHandler(oidcClientRegistrationResponseHandler);
            });
            oidc.userInfoEndpoint(userInfo -> userInfo
                    .userInfoMapper(new GoyaOidcUserInfoMapper()));
        });
    }
}
