package com.ysmjjsy.goya.security.authorization.server.domain.entity;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaEntity;
import com.ysmjjsy.goya.security.authorization.server.constants.SecurityAuthConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serial;
import java.util.Objects;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:13
 */
@Getter
@Setter
@ToString(callSuper = true)
@Schema(name = "系统权限")
@Entity
@Table(name = "security_permission", indexes = {@Index(name = "security_permission_id_idx", columnList = "id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityAuthConstants.REGION_SECURITY_PERMISSION)
public class SecurityPermission extends BaseJpaEntity {
    @Serial
    private static final long serialVersionUID = -35860705766793659L;

    @Schema(name = "权限代码")
    @Column(name = "permission_code", length = 128)
    private String permissionCode;

    @Schema(name = "权限名称")
    @Column(name = "permission_name", length = 1024)
    private String permissionName;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SecurityPermission that = (SecurityPermission) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
