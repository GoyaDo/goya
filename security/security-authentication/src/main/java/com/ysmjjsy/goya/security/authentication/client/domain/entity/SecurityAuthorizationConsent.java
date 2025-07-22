package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
import com.ysmjjsy.goya.security.authentication.client.domain.generator.GoyaAuthorizationConsentId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

/**
 * <p>Description: OAuth2 认证确认信息实体 </p>
 *
 * @author goya
 * @since 2022/1/22 17:55
 */
@Setter
@Getter
@Entity
@Table(name = "security_authorization_consent", indexes = {
        @Index(name = "security_authorization_consent_rcid_idx", columnList = "registered_client_id"),
        @Index(name = "security_authorization_consent_pn_idx", columnList = "principal_name")})
@IdClass(GoyaAuthorizationConsentId.class)
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_AUTHORIZATION_CONSENT)
public class SecurityAuthorizationConsent implements com.ysmjjsy.goya.component.dto.domain.Entity {

    @Serial
    private static final long serialVersionUID = -3705525280753746881L;

    @Id
    @Column(name = "registered_client_id", nullable = false, length = 100)
    private String registeredClientId;

    @Id
    @Column(name = "principal_name", nullable = false, length = 200)
    private String principalName;

    @Column(name = "authorities", nullable = false, length = 1000)
    private String authorities;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityAuthorizationConsent that = (SecurityAuthorizationConsent) o;
        return Objects.equal(registeredClientId, that.registeredClientId) && Objects.equal(principalName, that.principalName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registeredClientId, principalName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("registeredClientId", registeredClientId)
                .add("principalName", principalName)
                .add("authorities", authorities)
                .toString();
    }
}
