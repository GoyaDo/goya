package com.ysmjjsy.goya.security.authentication.consumer;

import com.ysmjjsy.goya.security.authentication.provider.OAuth2AuthorizationCodeAuthenticationProvider;
import com.ysmjjsy.goya.security.authentication.utils.OAuth2ConfigurerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Description: OAuth2AuthorizationCodeAuthenticationProvider 扩展 </p>
 * <p>
 * 用于替换 SAS 默认配置的 OAuth2AuthorizationCodeAuthenticationProvider，以实现功能的扩展
 *
 * @author goya
 * @since 2023/9/1 11:33
 */
public class OAuth2AuthorizationCodeAuthenticationProviderConsumer implements Consumer<List<AuthenticationProvider>> {

    private static final Logger log = LoggerFactory.getLogger(OAuth2AuthorizationCodeAuthenticationProviderConsumer.class);

    private final HttpSecurity httpSecurity;
    private final SessionRegistry sessionRegistry;

    public OAuth2AuthorizationCodeAuthenticationProviderConsumer(HttpSecurity httpSecurity, SessionRegistry sessionRegistry) {
        this.httpSecurity = httpSecurity;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void accept(List<AuthenticationProvider> authenticationProviders) {
        authenticationProviders.removeIf(authenticationProvider ->
                authenticationProvider instanceof org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationProvider);

        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);
        OAuth2AuthorizationCodeAuthenticationProvider provider = new OAuth2AuthorizationCodeAuthenticationProvider(authorizationService, tokenGenerator);
        provider.setSessionRegistry(sessionRegistry);
        log.debug("[Goya] |- Custom OAuth2AuthorizationCodeAuthenticationProvider is in effect!");
        authenticationProviders.add(provider);
    }
}
