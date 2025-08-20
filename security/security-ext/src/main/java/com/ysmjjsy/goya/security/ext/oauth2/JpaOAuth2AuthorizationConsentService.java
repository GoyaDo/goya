package com.ysmjjsy.goya.security.ext.oauth2;

import com.ysmjjsy.goya.security.ext.converter.OAuth2ToSecurityAuthorizationConsentConverter;
import com.ysmjjsy.goya.security.ext.converter.SecurityToOAuth2AuthorizationConsentConverter;
import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorizationConsent;
import com.ysmjjsy.goya.security.ext.service.SecurityAuthorizationConsentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * <p>基于 JPA 的 OAuth2 认证服务</p>
 *
 * @author goya
 * @since 2025/8/19 17:06
 */
@Slf4j
public class JpaOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

    private final SecurityAuthorizationConsentService securityAuthorizationConsentService;
    private final Converter<SecurityAuthorizationConsent, OAuth2AuthorizationConsent> securityToOAuth2Converter;
    private final Converter<OAuth2AuthorizationConsent, SecurityAuthorizationConsent> oauth2ToSecurityConverter;

    public JpaOAuth2AuthorizationConsentService(SecurityAuthorizationConsentService securityAuthorizationConsentService, RegisteredClientRepository registeredClientRepository) {
        this.securityAuthorizationConsentService = securityAuthorizationConsentService;
        this.securityToOAuth2Converter = new SecurityToOAuth2AuthorizationConsentConverter(registeredClientRepository);
        this.oauth2ToSecurityConverter = new OAuth2ToSecurityAuthorizationConsentConverter();
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        this.securityAuthorizationConsentService.save(toEntity(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        this.securityAuthorizationConsentService.deleteByRegisteredClientIdAndPrincipalName(
                authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return this.securityAuthorizationConsentService.findByRegisteredClientIdAndPrincipalName(
                registeredClientId, principalName).map(this::toObject).orElse(null);
    }

    private OAuth2AuthorizationConsent toObject(SecurityAuthorizationConsent authorizationConsent) {
        return securityToOAuth2Converter.convert(authorizationConsent);
    }

    private SecurityAuthorizationConsent toEntity(OAuth2AuthorizationConsent authorizationConsent) {
        return oauth2ToSecurityConverter.convert(authorizationConsent);
    }
}
