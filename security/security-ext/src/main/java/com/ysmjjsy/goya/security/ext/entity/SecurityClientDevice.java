package com.ysmjjsy.goya.security.ext.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.ext.constants.SecurityExtConstants;
import com.ysmjjsy.goya.security.ext.definition.AbstractOAuth2RegisteredClient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:46
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "物联网设备")
@Entity
@Table(name = "security_client_device",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"device_name"})},
        indexes = {@Index(name = "security_client_device_id_idx", columnList = "id"),
                @Index(name = "security_client_device_ipk_idx", columnList = "device_name"),
                @Index(name = "security_client_device_pid_idx", columnList = "product_id")
        })
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityExtConstants.REGION_CLIENT_DEVICE)
public class SecurityClientDevice extends AbstractOAuth2RegisteredClient {

    @Serial
    private static final long serialVersionUID = -1580623255091538329L;

    @Getter
    @Schema(name = "设备名称")
    @Column(name = "device_name", length = 64, unique = true)
    private String deviceName;

    @Getter
    @Schema(name = "产品ID")
    @Column(name = "product_id", length = 64)
    private String productId;

    @Getter
    @Schema(name = "是否已激活", title = "设备是否已经激活状态标记，默认值false，即未激活")
    @Column(name = "is_activated")
    private Boolean activated = Boolean.FALSE;

    @Schema(name = "设备对应Scope", title = "传递设备对应Scope ID数组")
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityExtConstants.REGION_CLIENT_APPLICATION_SCOPE)
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "security_client_device_scope",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"device_id", "scope_id"})},
            indexes = {@Index(name = "security_client_device_scope_aid_idx", columnList = "device_id"), @Index(name = "security_client_device_scope_sid_idx", columnList = "scope_id")})
    private Set<SecurityClientScope> scopes = new HashSet<>();

    @Override
    public Set<SecurityClientScope> getScopes() {
        return scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientDevice that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getDeviceName(), that.getDeviceName()) && Objects.equal(getProductId(), that.getProductId()) && Objects.equal(getActivated(), that.getActivated()) && Objects.equal(getScopes(), that.getScopes());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getDeviceName(), getProductId(), getActivated(), getScopes());
    }
}
