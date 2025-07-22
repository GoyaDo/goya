package com.ysmjjsy.goya.security.authentication.client.domain.converter;

import com.ysmjjsy.goya.security.authentication.client.domain.definition.AbstractOAuth2RegisteredClient;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.authentication.client.enums.AllJwsAlgorithm;
import com.ysmjjsy.goya.security.authentication.client.enums.SignatureJwsAlgorithm;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: OAuth2Application  </p>
 *
 * @author goya
 * @since 2023/5/13 10:34
 */
public abstract class AbstractOAuth2RegisteredClientConverter<T extends AbstractOAuth2RegisteredClient> implements RegisteredClientConverter<T> {

    @Override
    public Set<String> getScopes(T details) {
        Set<SecurityClientScope> clientScopes = details.getScopes();
        return clientScopes.stream().map(SecurityClientScope::getScopeCode).collect(Collectors.toSet());
    }

    @Override
    public ClientSettings getClientSettings(T details) {
        ClientSettings.Builder clientSettingsBuilder = ClientSettings.builder();
        clientSettingsBuilder.requireAuthorizationConsent(details.getRequireAuthorizationConsent());
        clientSettingsBuilder.requireProofKey(details.getRequireProofKey());
        if (StringUtils.hasText(details.getJwkSetUrl())) {
            clientSettingsBuilder.jwkSetUrl(details.getJwkSetUrl());
        }
        JwsAlgorithm jwsAlgorithm = toJwsAlgorithm(details.getAuthenticationSigningAlgorithm());
        if (ObjectUtils.isNotEmpty(jwsAlgorithm)) {
            clientSettingsBuilder.tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm);
        }
        if (StringUtils.hasText(details.getX509CertificateSubjectDN())) {
            clientSettingsBuilder.x509CertificateSubjectDN(details.getX509CertificateSubjectDN());
        }
        return clientSettingsBuilder.build();
    }

    @Override
    public TokenSettings getTokenSettings(T details) {
        TokenSettings.Builder tokenSettingsBuilder = TokenSettings.builder();
        tokenSettingsBuilder.authorizationCodeTimeToLive(details.getAuthorizationCodeValidity());
        tokenSettingsBuilder.accessTokenTimeToLive(details.getAccessTokenValidity());
        tokenSettingsBuilder.accessTokenFormat(new OAuth2TokenFormat(details.getAccessTokenFormat().getFormat()));
        tokenSettingsBuilder.deviceCodeTimeToLive(details.getDeviceCodeValidity());
        // 是否可重用刷新令牌
        tokenSettingsBuilder.reuseRefreshTokens(details.getReuseRefreshTokens());
        // refreshToken 的有效期
        tokenSettingsBuilder.refreshTokenTimeToLive(details.getRefreshTokenValidity());
        SignatureAlgorithm signatureAlgorithm = toSignatureAlgorithm(details.getIdTokenSignatureAlgorithmJwsAlgorithm());
        if (ObjectUtils.isNotEmpty(signatureAlgorithm)) {
            tokenSettingsBuilder.idTokenSignatureAlgorithm(signatureAlgorithm);
        }
        tokenSettingsBuilder.x509CertificateBoundAccessTokens(details.getX509CertificateBoundAccessTokens());
        return tokenSettingsBuilder.build();
    }

    private JwsAlgorithm toJwsAlgorithm(AllJwsAlgorithm allJwsAlgorithm) {
        if (ObjectUtils.isNotEmpty(allJwsAlgorithm)) {
            if (allJwsAlgorithm.getValue() < AllJwsAlgorithm.HS256.getValue()) {
                // 如果是签名算法, 转换成 SAS 签名算法
                return SignatureAlgorithm.from(allJwsAlgorithm.name());
            } else {
                // 如果是 Mac 算法, 转换成 Mac 签名算法
                return MacAlgorithm.from(allJwsAlgorithm.name());
            }
        }

        return null;
    }

    private SignatureAlgorithm toSignatureAlgorithm(SignatureJwsAlgorithm signatureJwsAlgorithm) {
        if (ObjectUtils.isNotEmpty(signatureJwsAlgorithm)) {
            return SignatureAlgorithm.from(signatureJwsAlgorithm.name());
        }

        return null;
    }
}
