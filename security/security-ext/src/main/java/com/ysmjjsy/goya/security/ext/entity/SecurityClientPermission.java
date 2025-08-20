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

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:37
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "security_client_permission", indexes = {@Index(name = "security_client_id_idx", columnList = "id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityExtConstants.REGION_CLIENT_PERMISSION)
public class SecurityClientPermission extends BaseJpaEntity {
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
