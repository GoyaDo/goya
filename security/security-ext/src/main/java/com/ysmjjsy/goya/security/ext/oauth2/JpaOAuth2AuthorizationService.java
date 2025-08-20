package com.ysmjjsy.goya.security.ext.oauth2;

import com.ysmjjsy.goya.security.ext.converter.OAuth2ToSecurityAuthorizationConverter;
import com.ysmjjsy.goya.security.ext.converter.SecurityToOAuth2AuthorizationConverter;
import com.ysmjjsy.goya.security.core.service.EnhanceOAuth2AuthorizationService;
import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorization;
import com.ysmjjsy.goya.security.ext.processor.OAuth2JacksonProcessor;
import com.ysmjjsy.goya.security.ext.service.SecurityAuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>基于 JPA 的 OAuth2 认证服务</p>
 *
 * @author goya
 * @since 2025/8/19 17:01
 */
@Slf4j
public class JpaOAuth2AuthorizationService implements EnhanceOAuth2AuthorizationService {

    private final SecurityAuthorizationService securityAuthorizationService;
    private final Converter<SecurityAuthorization, OAuth2Authorization> securityToOAuth2Converter;
    private final Converter<OAuth2Authorization, SecurityAuthorization> oauth2ToSecurityConverter;

    public JpaOAuth2AuthorizationService(SecurityAuthorizationService securityAuthorizationService, RegisteredClientRepository registeredClientRepository) {
        this.securityAuthorizationService = securityAuthorizationService;

        OAuth2JacksonProcessor jacksonProcessor = new OAuth2JacksonProcessor();
        this.securityToOAuth2Converter = new SecurityToOAuth2AuthorizationConverter(jacksonProcessor, registeredClientRepository);
        this.oauth2ToSecurityConverter = new OAuth2ToSecurityAuthorizationConverter(jacksonProcessor);
    }

    @Override
    public int findAuthorizationCount(String registeredClientId, String principalName) {
        int count = this.securityAuthorizationService.findAuthorizationCount(registeredClientId, principalName);
        log.debug("[Goya] |- Jpa OAuth2 Authorization Service findAuthorizationCount.");
        return count;
    }

    @Override
    public List<OAuth2Authorization> findAvailableAuthorizations(String registeredClientId, String principalName) {
        List<SecurityAuthorization> authorizations = this.securityAuthorizationService.findAvailableAuthorizations(registeredClientId, principalName);
        if (CollectionUtils.isNotEmpty(authorizations)) {
            return authorizations.stream().map(this::toObject).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        this.securityAuthorizationService.saveOrUpdate(toEntity(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.securityAuthorizationService.deleteById(authorization.getId());
        log.debug("[Goya] |- Jpa OAuth2 Authorization Service remove entity.");
        // TODO： 后期还是考虑改为异步任务的形式，先临时放在这里。
        this.securityAuthorizationService.clearHistoryToken();
        log.debug("[Goya] |- Jpa OAuth2 Authorization Service clear history token.");
    }

    @Override
    public OAuth2Authorization findById(String id) {
        SecurityAuthorization securityAuthorization = this.securityAuthorizationService.findById(id);
        if (ObjectUtils.isNotEmpty(securityAuthorization)) {
            return toObject(securityAuthorization);
        } else {
            return null;
        }
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        Optional<SecurityAuthorization> result;
        if (tokenType == null) {
            result = this.securityAuthorizationService.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByAuthorizationCode(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByAccessToken(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByRefreshToken(token);
        } else if (OidcParameterNames.ID_TOKEN.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByOidcIdTokenValue(token);
        } else if (OAuth2ParameterNames.USER_CODE.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByUserCodeValue(token);
        } else if (OAuth2ParameterNames.DEVICE_CODE.equals(tokenType.getValue())) {
            result = this.securityAuthorizationService.findByDeviceCodeValue(token);
        } else {
            result = Optional.empty();
        }

        return result.map(this::toObject).orElse(null);
    }

    private OAuth2Authorization toObject(SecurityAuthorization entity) {
        return securityToOAuth2Converter.convert(entity);
    }

    private SecurityAuthorization toEntity(OAuth2Authorization authorization) {
        return oauth2ToSecurityConverter.convert(authorization);
    }
}
