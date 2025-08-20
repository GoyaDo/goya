package com.ysmjjsy.goya.security.ext.converter;

import com.ysmjjsy.goya.security.ext.entity.SecurityRegisteredClient;
import com.ysmjjsy.goya.security.ext.processor.OAuth2JacksonProcessor;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * <p>Description: SecurityRegisteredClient 转 换适配器 </p>
 *
 * @author goya
 * @since 2023/5/12 23:56
 */
public class SecurityToOAuth2RegisteredClientConverter extends AbstractRegisteredClientConverter<SecurityRegisteredClient> {

    public SecurityToOAuth2RegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override
    public Set<String> getScopes(SecurityRegisteredClient details) {
        return StringUtils.commaDelimitedListToSet(details.getScopes());
    }

    @Override
    public ClientSettings getClientSettings(SecurityRegisteredClient details) {
        Map<String, Object> clientSettingsMap = parseMap(details.getClientSettings());
        return ClientSettings.withSettings(clientSettingsMap).build();
    }

    @Override
    public TokenSettings getTokenSettings(SecurityRegisteredClient details) {
        Map<String, Object> tokenSettingsMap = parseMap(details.getTokenSettings());
        return TokenSettings.withSettings(tokenSettingsMap).build();
    }
}
