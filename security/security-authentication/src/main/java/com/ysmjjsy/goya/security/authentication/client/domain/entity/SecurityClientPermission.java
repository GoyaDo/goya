package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:37
 */
@ToString(callSuper = true)
@Setter
@Getter
@Entity
@Table(name = "security_client_permission", indexes = {@Index(name = "security_client_id_idx", columnList = "permission_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_PERMISSION)
public class SecurityClientPermission extends BaseJpaAggregate {
    @Serial
    private static final long serialVersionUID = 8888945227236139060L;

    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Column(name = "permission_name", length = 128)
    private String permissionName;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientPermission that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getPermissionCode(), that.getPermissionCode()) && Objects.equal(getPermissionName(), that.getPermissionName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getPermissionCode(), getPermissionName());
    }
}
