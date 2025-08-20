package com.ysmjjsy.goya.security.ext.converter;

import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorizationConsent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Description: OAuth2AuthorizationConsent 转 SecurityAuthorizationConsent 转换器</p>
 *
 * @author goya
 * @since 2023/5/21 21:05
 */
public class OAuth2ToSecurityAuthorizationConsentConverter implements Converter<OAuth2AuthorizationConsent, SecurityAuthorizationConsent> {
    @Override
    public SecurityAuthorizationConsent convert(OAuth2AuthorizationConsent authorizationConsent) {
        SecurityAuthorizationConsent entity = new SecurityAuthorizationConsent();
        entity.setRegisteredClientId(authorizationConsent.getRegisteredClientId());
        entity.setPrincipalName(authorizationConsent.getPrincipalName());

        Set<String> authorities = new HashSet<>();
        for (GrantedAuthority authority : authorizationConsent.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }
        entity.setAuthorities(StringUtils.collectionToCommaDelimitedString(authorities));

        return entity;
    }
}
