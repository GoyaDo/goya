package com.ysmjjsy.goya.security.ext.converter;

import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;
import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorizationConsent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.StringUtils;

/**
 * <p>Description: SecurityAuthorizationConsent 转 OAuth2AuthorizationConsent 转换器 </p>
 *
 * @author goya
 * @since 2023/5/21 21:03
 */
public class SecurityToOAuth2AuthorizationConsentConverter implements Converter<SecurityAuthorizationConsent, OAuth2AuthorizationConsent> {

    private final RegisteredClientRepository registeredClientRepository;

    public SecurityToOAuth2AuthorizationConsentConverter(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public OAuth2AuthorizationConsent convert(SecurityAuthorizationConsent authorizationConsent) {
        String registeredClientId = authorizationConsent.getRegisteredClientId();
        RegisteredClient registeredClient = this.registeredClientRepository.findById(registeredClientId);
        if (registeredClient == null) {
            throw new DataRetrievalFailureException(
                    "The RegisteredClient with id '" + registeredClientId + "' was not found in the RegisteredClientRepository.");
        }

        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent.withId(
                registeredClientId, authorizationConsent.getPrincipalName());
        if (authorizationConsent.getAuthorities() != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorizationConsent.getAuthorities())) {
                builder.authority(new GoyaGrantedAuthority(authority));
            }
        }

        return builder.build();
    }
}
