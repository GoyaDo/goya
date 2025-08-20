package com.ysmjjsy.goya.security.authentication.consumer;

import com.ysmjjsy.goya.security.authentication.provider.OidcClientRegistrationToRegisteredClientConverter;
import com.ysmjjsy.goya.security.authentication.provider.RegisteredClientToOidcClientRegistrationConverter;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientConfigurationAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationProvider;

import java.util.List;
import java.util.function.Consumer;

/**
 * <p>Description: 客户端动态注册自定义属性 </p>
 *
 * @author goya
 * @since 2024/5/16 16:37
 */
public class OidcClientRegistrationAuthenticationProviderConsumer implements Consumer<List<AuthenticationProvider>> {

    private static final List<String> clientMetadata = List.of(SecurityConstants.PRODUCT_ID);

    @Override
    public void accept(List<AuthenticationProvider> authenticationProviders) {

        Converter<OidcClientRegistration, RegisteredClient> toRegisteredClientConverter =
                new OidcClientRegistrationToRegisteredClientConverter(clientMetadata);
        Converter<RegisteredClient, OidcClientRegistration> toOidcClientRegistrationConverter =
                new RegisteredClientToOidcClientRegistrationConverter(clientMetadata);

        authenticationProviders.forEach((authenticationProvider) -> {
            if (authenticationProvider instanceof OidcClientRegistrationAuthenticationProvider provider) {
                provider.setRegisteredClientConverter(toRegisteredClientConverter);
                provider.setClientRegistrationConverter(toOidcClientRegistrationConverter);
            }
            if (authenticationProvider instanceof OidcClientConfigurationAuthenticationProvider provider) {
                provider.setClientRegistrationConverter(toOidcClientRegistrationConverter);
            }
        });

    }
}
