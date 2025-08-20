package com.ysmjjsy.goya.security.authentication.provider;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.converter.OidcClientRegistrationRegisteredClientConverter;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.List;

/**
 * <p>Description: OidcClientRegistration 转 RegisteredClient 转换器</p>
 *
 * @author goya
 * @since 2024/5/16 16:18
 */
public class OidcClientRegistrationToRegisteredClientConverter implements Converter<OidcClientRegistration, RegisteredClient> {

    private final List<String> clientMetadata;
    private final OidcClientRegistrationRegisteredClientConverter delegate;

    public OidcClientRegistrationToRegisteredClientConverter(List<String> clientMetadata) {
        this.clientMetadata = clientMetadata;
        this.delegate = new OidcClientRegistrationRegisteredClientConverter();
    }

    @Override
    public RegisteredClient convert(OidcClientRegistration oidcClientRegistration) {
        RegisteredClient registeredClient = this.delegate.convert(oidcClientRegistration);
        ClientSettings.Builder clientSettingsBuilder = ClientSettings.withSettings(registeredClient.getClientSettings().getSettings());
        if (CollectionUtils.isNotEmpty(this.clientMetadata)) {
            oidcClientRegistration.getClaims().forEach((claim, value) -> {
                if (this.clientMetadata.contains(claim)) {
                    clientSettingsBuilder.setting(claim, value);
                }
            });
        }

        return RegisteredClient.from(registeredClient)
                .clientSettings(clientSettingsBuilder.build())
                .build();
    }
}
