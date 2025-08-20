package com.ysmjjsy.goya.security.authentication.configurer;

import com.ysmjjsy.goya.module.identity.configuration.properties.IdentityProperties;
import com.ysmjjsy.goya.security.authentication.provider.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.ysmjjsy.goya.security.authentication.provider.OAuth2SocialCredentialsAuthenticationProvider;
import com.ysmjjsy.goya.security.authentication.utils.OAuth2ConfigurerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * <p>Description: 自定义 AuthenticationProvider 配置器 </p>
 *
 * @author goya
 * @since 2023/9/1 15:46
 */
@RequiredArgsConstructor
public class OAuth2AuthenticationProviderConfigurer extends AbstractHttpConfigurer<OAuth2AuthenticationProviderConfigurer, HttpSecurity> {

    private final SessionRegistry sessionRegistry;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final IdentityProperties identityProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationService authorizationService = OAuth2ConfigurerUtils.getAuthorizationService(httpSecurity);
        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator = OAuth2ConfigurerUtils.getTokenGenerator(httpSecurity);

        OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider =
                new OAuth2ResourceOwnerPasswordAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, identityProperties);
        resourceOwnerPasswordAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        resourceOwnerPasswordAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);

        OAuth2SocialCredentialsAuthenticationProvider socialCredentialsAuthenticationProvider =
                new OAuth2SocialCredentialsAuthenticationProvider(authorizationService, tokenGenerator, userDetailsService, identityProperties);
        socialCredentialsAuthenticationProvider.setSessionRegistry(sessionRegistry);
        httpSecurity.authenticationProvider(socialCredentialsAuthenticationProvider);
    }
}
