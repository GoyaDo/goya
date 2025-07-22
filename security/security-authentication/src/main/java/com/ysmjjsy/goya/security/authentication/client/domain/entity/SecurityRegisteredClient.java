package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
import com.ysmjjsy.goya.security.authentication.client.domain.definition.AbstractRegisteredClient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;
import java.util.Objects;

/**
 * <p>Description: OAuth2 客户端实体 </p>
 *
 * @author goya
 * @since 2022/1/22 17:18
 */
@Setter
@Entity
@Table(name = "security_registered_client", indexes = {
        @Index(name = "security_registered_client_id_idx", columnList = "id"),
        @Index(name = "security_registered_client_cid_idx", columnList = "client_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_REGISTERED_CLIENT)
public class SecurityRegisteredClient extends AbstractRegisteredClient {

    @Serial
    private static final long serialVersionUID = -364530867192195090L;

    @Column(name = "client_id", nullable = false, length = 100)
    private String clientId;

    @Column(name = "client_secret", length = 200)
    private String clientSecret;

    @Getter
    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;

    @Getter
    @Column(name = "scopes", nullable = false, length = 1000)
    private String scopes;

    @Getter
    @Column(name = "client_settings", nullable = false, length = 2000)
    private String clientSettings;

    @Getter
    @Column(name = "token_settings", nullable = false, length = 2000)
    private String tokenSettings;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecurityRegisteredClient that = (SecurityRegisteredClient) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("clientId", clientId)
                .add("clientSecret", clientSecret)
                .add("clientName", clientName)
                .add("scopes", scopes)
                .add("clientSettings", clientSettings)
                .add("tokenSettings", tokenSettings)
                .toString();
    }
}
