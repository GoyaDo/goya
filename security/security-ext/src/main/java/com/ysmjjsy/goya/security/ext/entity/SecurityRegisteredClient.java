package com.ysmjjsy.goya.security.ext.entity;

import com.ysmjjsy.goya.security.ext.constants.SecurityExtConstants;
import com.ysmjjsy.goya.security.ext.definition.AbstractRegisteredClient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

/**
 * <p>Description: OAuth2 客户端实体 </p>
 *
 * @author goya
 * @since 2022/1/22 17:18
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "security_registered_client", indexes = {
        @Index(name = "security_registered_client_id_idx", columnList = "id"),
        @Index(name = "security_registered_client_cid_idx", columnList = "client_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityExtConstants.REGION_CLIENT_REGISTERED_CLIENT)
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

}
