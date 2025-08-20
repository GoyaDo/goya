package com.ysmjjsy.goya.security.ext.converter;

import cn.hutool.v7.core.date.DateUtil;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.enums.AllJwsAlgorithm;
import com.ysmjjsy.goya.security.core.enums.SignatureJwsAlgorithm;
import com.ysmjjsy.goya.security.core.enums.TokenFormat;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientDevice;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.ext.service.SecurityClientScopeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: OAuth2Device 转 RegisteredClient 转换器 </p>
 *
 * @author goya
 * @since 2023/5/21 22:05
 */
@RequiredArgsConstructor
public class RegisteredClientToOAuth2DeviceConverter implements Converter<RegisteredClient, SecurityClientDevice> {

    private final SecurityClientScopeService securityClientScopeService;

    @Override
    public SecurityClientDevice convert(RegisteredClient registeredClient) {

        SecurityClientDevice device = new SecurityClientDevice();
        device.setId(registeredClient.getId());
        device.setDeviceName(registeredClient.getClientName());
        device.setScopes(getOAuth2Scopes(registeredClient.getScopes()));
        device.setClientId(registeredClient.getClientId());
        device.setClientSecret(registeredClient.getClientSecret());
        device.setClientIdIssuedAt(DateUtil.toLocalDateTime(registeredClient.getClientIdIssuedAt()));
        device.setClientSecretExpiresAt(DateUtil.toLocalDateTime(registeredClient.getClientSecretExpiresAt()));
        device.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(registeredClient.getClientAuthenticationMethods()));
        device.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(registeredClient.getAuthorizationGrantTypes().stream().map(AuthorizationGrantType::getValue).collect(Collectors.toSet())));
        device.setRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));
        device.setPostLogoutRedirectUris(StringUtils.collectionToCommaDelimitedString(registeredClient.getRedirectUris()));

        ClientSettings clientSettings = registeredClient.getClientSettings();
        device.setRequireProofKey(clientSettings.isRequireProofKey());
        device.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        device.setJwkSetUrl(clientSettings.getJwkSetUrl());
        if (ObjectUtils.isNotEmpty(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm())) {
            device.setAuthenticationSigningAlgorithm(AllJwsAlgorithm.valueOf(clientSettings.getTokenEndpointAuthenticationSigningAlgorithm().getName()));
        }

        String productId = clientSettings.getSetting(SecurityConstants.PRODUCT_ID);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(productId)) {
            device.setProductId(productId);
        }

        TokenSettings tokenSettings = registeredClient.getTokenSettings();
        device.setAuthorizationCodeValidity(tokenSettings.getAuthorizationCodeTimeToLive());
        device.setAccessTokenValidity(tokenSettings.getAccessTokenTimeToLive());
        device.setDeviceCodeValidity(tokenSettings.getDeviceCodeTimeToLive());
        device.setRefreshTokenValidity(tokenSettings.getRefreshTokenTimeToLive());
        device.setAccessTokenFormat(TokenFormat.get(tokenSettings.getAccessTokenFormat().getValue()));
        device.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        device.setIdTokenSignatureAlgorithmJwsAlgorithm(SignatureJwsAlgorithm.valueOf(tokenSettings.getIdTokenSignatureAlgorithm().getName()));

        return device;
    }

    private Set<SecurityClientScope> getOAuth2Scopes(Set<String> scopes) {
        if (CollectionUtils.isNotEmpty(scopes)) {
            List<String> scopeCodes = new ArrayList<>(scopes);
            List<SecurityClientScope> result = securityClientScopeService.findByScopeCodeIn(scopeCodes);
            if (CollectionUtils.isNotEmpty(result)) {
                return new HashSet<>(result);
            }
        }
        return new HashSet<>();
    }
}
