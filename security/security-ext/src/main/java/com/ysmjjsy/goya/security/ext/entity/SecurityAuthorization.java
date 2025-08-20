package com.ysmjjsy.goya.security.ext.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.module.jpa.domain.BaseJpaEntity;
import com.ysmjjsy.goya.security.ext.constants.SecurityExtConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>Description: OAuth2 认证信息 </p>
 *
 * @author goya
 * @since 2022/1/22 18:08
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "security_authorization", indexes = {
        @Index(name = "security_authorization_id_idx", columnList = "id"),
        @Index(name = "security_authorization_rci_idx", columnList = "registered_client_id"),
        @Index(name = "security_authorization_pn_idx", columnList = "principal_name")}
)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityExtConstants.REGION_CLIENT_AUTHORIZATION)
public class SecurityAuthorization extends BaseJpaEntity {

    @Serial
    private static final long serialVersionUID = 3493360483403866986L;

    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Column(name = "authorization_grant_type", nullable = false, length = 100)
    private String authorizationGrantType;

    @Column(name = "authorized_scopes", length = 1000)
    private String authorizedScopes;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes;

    @Column(name = "state", length = 500)
    private String state;

    @Column(name = "authorization_code_value", columnDefinition = "TEXT")
    private String authorizationCodeValue;

    @Column(name = "authorization_code_issued_at")
    private LocalDateTime authorizationCodeIssuedAt;

    @Column(name = "authorization_code_expires_at")
    private LocalDateTime authorizationCodeExpiresAt;

    @Column(name = "authorization_code_metadata", columnDefinition = "TEXT")
    private String authorizationCodeMetadata;

    @Column(name = "access_token_value", columnDefinition = "TEXT")
    private String accessTokenValue;

    @Column(name = "access_token_issued_at")
    private LocalDateTime accessTokenIssuedAt;

    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    @Column(name = "access_token_metadata", columnDefinition = "TEXT")
    private String accessTokenMetadata;

    @Column(name = "access_token_type", length = 100)
    private String accessTokenType;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Column(name = "oidc_id_token_value", columnDefinition = "TEXT")
    private String oidcIdTokenValue;

    @Column(name = "oidc_id_token_issued_at")
    private LocalDateTime oidcIdTokenIssuedAt;

    @Column(name = "oidc_id_token_expires_at")
    private LocalDateTime oidcIdTokenExpiresAt;

    @Column(name = "oidc_id_token_metadata", columnDefinition = "TEXT")
    private String oidcIdTokenMetadata;

    @Column(name = "oidc_id_token_claims", length = 2000)
    private String oidcIdTokenClaims;

    @Column(name = "refresh_token_value", columnDefinition = "TEXT")
    private String refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private LocalDateTime refreshTokenIssuedAt;

    @Column(name = "refresh_token_expires_at")
    private LocalDateTime refreshTokenExpiresAt;

    @Column(name = "refresh_token_metadata", columnDefinition = "TEXT")
    private String refreshTokenMetadata;

    @Column(name = "user_code_value", columnDefinition = "TEXT")
    private String userCodeValue;

    @Column(name = "user_code_issued_at")
    private LocalDateTime userCodeIssuedAt;

    @Column(name = "user_code_expires_at")
    private LocalDateTime userCodeExpiresAt;

    @Column(name = "user_code_metadata", columnDefinition = "TEXT")
    private String userCodeMetadata;

    @Column(name = "device_code_value", columnDefinition = "TEXT")
    private String deviceCodeValue;

    @Column(name = "device_code_issued_at")
    private LocalDateTime deviceCodeIssuedAt;

    @Column(name = "device_code_expires_at")
    private LocalDateTime deviceCodeExpiresAt;

    @Column(name = "device_code_metadata", columnDefinition = "TEXT")
    private String deviceCodeMetadata;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        SecurityAuthorization that = (SecurityAuthorization) o;
        return Objects.equal(getRegisteredClientId(), that.getRegisteredClientId()) && Objects.equal(getPrincipalName(), that.getPrincipalName()) && Objects.equal(getAuthorizationGrantType(), that.getAuthorizationGrantType()) && Objects.equal(getAuthorizedScopes(), that.getAuthorizedScopes()) && Objects.equal(getAttributes(), that.getAttributes()) && Objects.equal(getState(), that.getState()) && Objects.equal(getAuthorizationCodeValue(), that.getAuthorizationCodeValue()) && Objects.equal(getAuthorizationCodeIssuedAt(), that.getAuthorizationCodeIssuedAt()) && Objects.equal(getAuthorizationCodeExpiresAt(), that.getAuthorizationCodeExpiresAt()) && Objects.equal(getAuthorizationCodeMetadata(), that.getAuthorizationCodeMetadata()) && Objects.equal(getAccessTokenValue(), that.getAccessTokenValue()) && Objects.equal(getAccessTokenIssuedAt(), that.getAccessTokenIssuedAt()) && Objects.equal(getAccessTokenExpiresAt(), that.getAccessTokenExpiresAt()) && Objects.equal(getAccessTokenMetadata(), that.getAccessTokenMetadata()) && Objects.equal(getAccessTokenType(), that.getAccessTokenType()) && Objects.equal(getAccessTokenScopes(), that.getAccessTokenScopes()) && Objects.equal(getOidcIdTokenValue(), that.getOidcIdTokenValue()) && Objects.equal(getOidcIdTokenIssuedAt(), that.getOidcIdTokenIssuedAt()) && Objects.equal(getOidcIdTokenExpiresAt(), that.getOidcIdTokenExpiresAt()) && Objects.equal(getOidcIdTokenMetadata(), that.getOidcIdTokenMetadata()) && Objects.equal(getOidcIdTokenClaims(), that.getOidcIdTokenClaims()) && Objects.equal(getRefreshTokenValue(), that.getRefreshTokenValue()) && Objects.equal(getRefreshTokenIssuedAt(), that.getRefreshTokenIssuedAt()) && Objects.equal(getRefreshTokenExpiresAt(), that.getRefreshTokenExpiresAt()) && Objects.equal(getRefreshTokenMetadata(), that.getRefreshTokenMetadata()) && Objects.equal(getUserCodeValue(), that.getUserCodeValue()) && Objects.equal(getUserCodeIssuedAt(), that.getUserCodeIssuedAt()) && Objects.equal(getUserCodeExpiresAt(), that.getUserCodeExpiresAt()) && Objects.equal(getUserCodeMetadata(), that.getUserCodeMetadata()) && Objects.equal(getDeviceCodeValue(), that.getDeviceCodeValue()) && Objects.equal(getDeviceCodeIssuedAt(), that.getDeviceCodeIssuedAt()) && Objects.equal(getDeviceCodeExpiresAt(), that.getDeviceCodeExpiresAt()) && Objects.equal(getDeviceCodeMetadata(), that.getDeviceCodeMetadata());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getRegisteredClientId(), getPrincipalName(), getAuthorizationGrantType(), getAuthorizedScopes(), getAttributes(), getState(), getAuthorizationCodeValue(), getAuthorizationCodeIssuedAt(), getAuthorizationCodeExpiresAt(), getAuthorizationCodeMetadata(), getAccessTokenValue(), getAccessTokenIssuedAt(), getAccessTokenExpiresAt(), getAccessTokenMetadata(), getAccessTokenType(), getAccessTokenScopes(), getOidcIdTokenValue(), getOidcIdTokenIssuedAt(), getOidcIdTokenExpiresAt(), getOidcIdTokenMetadata(), getOidcIdTokenClaims(), getRefreshTokenValue(), getRefreshTokenIssuedAt(), getRefreshTokenExpiresAt(), getRefreshTokenMetadata(), getUserCodeValue(), getUserCodeIssuedAt(), getUserCodeExpiresAt(), getUserCodeMetadata(), getDeviceCodeValue(), getDeviceCodeIssuedAt(), getDeviceCodeExpiresAt(), getDeviceCodeMetadata());
    }
}
