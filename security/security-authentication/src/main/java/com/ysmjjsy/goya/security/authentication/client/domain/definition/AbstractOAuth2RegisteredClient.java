package com.ysmjjsy.goya.security.authentication.client.domain.definition;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.authentication.client.enums.AllJwsAlgorithm;
import com.ysmjjsy.goya.security.authentication.client.enums.SignatureJwsAlgorithm;
import com.ysmjjsy.goya.security.authentication.client.enums.TokenFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dromara.hutool.core.data.id.IdUtil;

import java.io.Serial;
import java.time.Duration;
import java.util.Set;

/**
 * <p>应用对象转 RegisteredClient 共性属性</p>
 *
 * @author goya
 * @since 2025/7/21 23:27
 */
@ToString(callSuper = true)
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractOAuth2RegisteredClient extends AbstractRegisteredClient {

    @Serial
    private static final long serialVersionUID = 3620768454646765688L;

    @Schema(name = "客户端Id", title = "默认为系统自动生成")
    @Column(name = "client_id", length = 100)
    private String clientId = IdUtil.fastSimpleUUID();

    @Schema(name = "客户端秘钥", title = "这里存储的客户端秘钥是明文，方便使用。默认为系统自动生成")
    @Column(name = "client_secret", length = 100)
    private String clientSecret = IdUtil.fastSimpleUUID();

    /* --- ClientSettings Begin --- */
    @Schema(name = "是否需要证明Key", title = "如果客户端在执行授权码授予流时需要提供验证密钥质询和验证器, 默认False")
    @Column(name = "require_proof_key")
    private Boolean requireProofKey = Boolean.FALSE;

    @Schema(name = "是否需要认证确认", title = "如果客户端在执行授权码授予流时需要提供验证密钥质询和验证器, 默认False")
    @Column(name = "require_authorization_consent")
    private Boolean requireAuthorizationConsent = Boolean.TRUE;

    @Schema(name = "客户端JSON Web密钥集的URL", title = "客户端JSON Web密钥集的URL")
    @Column(name = "jwk_set_url", length = 1000)
    private String jwkSetUrl;

    @Schema(name = "JWT 签名算法", title = "仅在 clientAuthenticationMethods 为 private_key_jwt 和 client_secret_jwt 方法下使用")
    @Column(name = "signing_algorithm")
    @Enumerated(EnumType.ORDINAL)
    private AllJwsAlgorithm authenticationSigningAlgorithm;

    @Schema(name = "X509证书DN")
    @Column(name = "subject_dn")
    private String x509CertificateSubjectDN;
    /* --- ClientSettings End --- */


    /* --- TokenSettings Begin --- */
    @Schema(name = "授权码有效时间", title = "默认5分钟，使用 Duration 时间格式")
    @Column(name = "authorization_code_validity")
    private Duration authorizationCodeValidity = Duration.ofMinutes(5);

    @Schema(name = "激活码有效时间", title = "默认5分钟，使用 Duration 时间格式")
    @Column(name = "device_code_validity")
    private Duration deviceCodeValidity = Duration.ofMinutes(5);

    @Schema(name = "AccessToken 有效时间", title = "默认5分钟，使用 Duration 时间格式")
    @Column(name = "access_token_validity")
    private Duration accessTokenValidity = Duration.ofMinutes(5);

    @Schema(name = "RefreshToken 有效时间", title = "默认60分钟，使用 Duration 时间格式")
    @Column(name = "refresh_token_validity")
    private Duration refreshTokenValidity = Duration.ofMinutes(60);

    @Schema(name = "Access Token 格式", title = "OAuth 2.0令牌的标准数据格式")
    @Column(name = "access_token_format")
    @Enumerated(EnumType.ORDINAL)
    private TokenFormat accessTokenFormat = TokenFormat.REFERENCE;

    @Schema(name = "是否重用 Refresh Token", title = "默认值 True")
    @Column(name = "reuse_refresh_tokens")
    private Boolean reuseRefreshTokens = Boolean.TRUE;

    @Schema(name = "IdToken 签名算法", title = "JWT 算法用于签名 ID Token， 默认值 RS256")
    @Column(name = "signature_algorithm")
    @Enumerated(EnumType.ORDINAL)
    private SignatureJwsAlgorithm idTokenSignatureAlgorithmJwsAlgorithm = SignatureJwsAlgorithm.RS256;

    @Schema(name = "X509证书是否绑定 AccessToken", title = "默认值 false")
    @Column(name = "bound_access_token")
    private Boolean x509CertificateBoundAccessTokens = Boolean.FALSE;
    /* --- TokenSettings End --- */

    public abstract Set<SecurityClientScope> getScopes();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractOAuth2RegisteredClient that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getClientId(), that.getClientId()) && Objects.equal(getClientSecret(), that.getClientSecret()) && Objects.equal(requireProofKey, that.requireProofKey) && Objects.equal(requireAuthorizationConsent, that.requireAuthorizationConsent) && Objects.equal(jwkSetUrl, that.jwkSetUrl) && authenticationSigningAlgorithm == that.authenticationSigningAlgorithm && Objects.equal(x509CertificateSubjectDN, that.x509CertificateSubjectDN) && Objects.equal(authorizationCodeValidity, that.authorizationCodeValidity) && Objects.equal(deviceCodeValidity, that.deviceCodeValidity) && Objects.equal(accessTokenValidity, that.accessTokenValidity) && Objects.equal(refreshTokenValidity, that.refreshTokenValidity) && accessTokenFormat == that.accessTokenFormat && Objects.equal(reuseRefreshTokens, that.reuseRefreshTokens) && idTokenSignatureAlgorithmJwsAlgorithm == that.idTokenSignatureAlgorithmJwsAlgorithm && Objects.equal(x509CertificateBoundAccessTokens, that.x509CertificateBoundAccessTokens);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getClientId(), getClientSecret(), requireProofKey, requireAuthorizationConsent, jwkSetUrl, authenticationSigningAlgorithm, x509CertificateSubjectDN, authorizationCodeValidity, deviceCodeValidity, accessTokenValidity, refreshTokenValidity, accessTokenFormat, reuseRefreshTokens, idTokenSignatureAlgorithmJwsAlgorithm, x509CertificateBoundAccessTokens);
    }
}
