package com.ysmjjsy.goya.security.authentication.client.domain.definition;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.common.json.jackson2.deserializer.CommaDelimitedStringToSetSerializer;
import com.ysmjjsy.goya.component.common.json.jackson2.deserializer.SetToCommaDelimitedStringDeserializer;
import com.ysmjjsy.goya.component.pojo.constants.DefaultConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:24
 */
@ToString(callSuper = true)
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractRegisteredClient extends BaseJpaAggregate implements RegisteredClientDetails{

    @Serial
    private static final long serialVersionUID = -7543872975020979274L;

    @Schema(name = "客户端ID发布日期", title = "客户端发布日期")
    @JsonFormat(pattern = DefaultConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    @Column(name = "client_id_issued_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime clientIdIssuedAt;

    @Schema(name = "客户端秘钥过期时间", title = "客户端秘钥过期时间")
    @JsonFormat(pattern = DefaultConstants.DATE_TIME_FORMAT, locale = "GMT+8", shape = JsonFormat.Shape.STRING)
    @Column(name = "client_secret_expires_at")
    private LocalDateTime clientSecretExpiresAt;

    @Schema(name = "客户端认证模式", title = "支持多个值，以逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "client_authentication_methods", nullable = false, length = 1000)
    @JsonDeserialize(using = SetToCommaDelimitedStringDeserializer.class)
    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    private String clientAuthenticationMethods;

    @Schema(name = "认证模式", title = "支持多个值，以逗号分隔", requiredMode = Schema.RequiredMode.REQUIRED)
    @Column(name = "authorization_grant_types", nullable = false, length = 1000)
    @JsonDeserialize(using = SetToCommaDelimitedStringDeserializer.class)
    @JsonSerialize(using = CommaDelimitedStringToSetSerializer.class)
    private String authorizationGrantTypes;

    @Schema(name = "回调地址", title = "支持多个值，以逗号分隔")
    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    @Schema(name = "OIDC Logout 回调地址", title = "支持多个值，以逗号分隔")
    @Column(name = "post_logout_redirect_uris", length = 1000)
    private String postLogoutRedirectUris;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractRegisteredClient that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getClientIdIssuedAt(), that.getClientIdIssuedAt()) && Objects.equal(getClientSecretExpiresAt(), that.getClientSecretExpiresAt()) && Objects.equal(getClientAuthenticationMethods(), that.getClientAuthenticationMethods()) && Objects.equal(getAuthorizationGrantTypes(), that.getAuthorizationGrantTypes()) && Objects.equal(getRedirectUris(), that.getRedirectUris()) && Objects.equal(getPostLogoutRedirectUris(), that.getPostLogoutRedirectUris());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getClientIdIssuedAt(), getClientSecretExpiresAt(), getClientAuthenticationMethods(), getAuthorizationGrantTypes(), getRedirectUris(), getPostLogoutRedirectUris());
    }
}
