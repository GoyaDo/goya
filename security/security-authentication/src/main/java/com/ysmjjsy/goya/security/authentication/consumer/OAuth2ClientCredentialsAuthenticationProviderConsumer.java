package com.ysmjjsy.goya.security.authentication.consumer;

import com.ysmjjsy.goya.security.authentication.provider.OAuth2ClientCredentialsAuthenticationProvider;
import com.ysmjjsy.goya.security.authentication.utils.OAuth2ConfigurerUtils;
import com.ysmjjsy.goya.security.core.service.ClientDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Description: OAuth2ClientCredentialsAuthenticationProvider 扩展 </p>
 * <p>
 * 用于替换 SAS 默认配置的 OAuth2ClientCredentialsAuthenticationProvider，以实现功能的扩展
 *
 * @author goya
 * @since 2023/9/1 14:29
 */
public class OAuth2ClientCredentialsAuthenticationProviderConsumer implements Consumer<List<AuthenticationProvider>> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2ClientCredentialsAuthenticationProviderConsumer.class);

    private final HttpSecurity httpSecurity;
    private final ClientDetailsService clientDetailsService;

    public OAuth2ClientCredentialsAuthenticationProviderConsumer(HttpSecurity httpSecurity, ClientDetailsService clientDetailsService) {
        this.httpSecurity = httpSecurity;
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    public void accept(List<AuthenticationProvider> authenticationProviders) {
        authenticationProviders.removeIf(authenticationProvider ->
                authenticationProvider instanceof org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientCredentialsAuthenticationProvider);

        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);
        OAuth2ClientCredentialsAuthenticationProvider provider = new OAuth2ClientCredentialsAuthenticationProvider(authorizationService, tokenGenerator, clientDetailsService);
        log.debug("[Goya] |- Custom OAuth2ClientCredentialsAuthenticationProvider is in effect!");
        authenticationProviders.add(provider);
    }
}
