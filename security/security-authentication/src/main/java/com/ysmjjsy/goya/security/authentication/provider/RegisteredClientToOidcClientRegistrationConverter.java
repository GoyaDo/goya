package com.ysmjjsy.goya.security.authentication.provider;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.converter.RegisteredClientOidcClientRegistrationConverter;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Description: RegisteredClient 转 OidcClientRegistration 转换器 </p>
 *
 * @author goya
 * @since 2024/5/16 16:29
 */
public class RegisteredClientToOidcClientRegistrationConverter implements Converter<RegisteredClient, OidcClientRegistration> {

    private final List<String> clientMetadata;
    private final RegisteredClientOidcClientRegistrationConverter delegate;

    public RegisteredClientToOidcClientRegistrationConverter(List<String> clientMetadata) {
        this.clientMetadata = clientMetadata;
        this.delegate = new RegisteredClientOidcClientRegistrationConverter();
    }

    @Override
    public OidcClientRegistration convert(RegisteredClient registeredClient) {
        OidcClientRegistration clientRegistration = this.delegate.convert(registeredClient);

        Map<String, Object> claims = new HashMap<>(clientRegistration.getClaims());
        if (CollectionUtils.isNotEmpty(this.clientMetadata)) {
            ClientSettings clientSettings = registeredClient.getClientSettings();
            claims.putAll(this.clientMetadata.stream()
                    .filter(metadata -> clientSettings.getSetting(metadata) != null)
                    .collect(Collectors.toMap(Function.identity(), clientSettings::getSetting)));
        }

        return OidcClientRegistration.withClaims(claims).build();
    }
}
