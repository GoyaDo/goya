package com.ysmjjsy.goya.security.authentication.client.domain.entity;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.security.authentication.client.constants.SecurityClientConstants;
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
 * @since 2025/7/21 23:33
 */
@ToString(callSuper = true)
@Setter
@Getter
@Entity
@Table(name = "security_client_scope", uniqueConstraints = {@UniqueConstraint(columnNames = {"scope_code"})}, indexes = {
        @Index(name = "security_client_scope_id_idx", columnList = "scope_id"),
        @Index(name = "security_client_scope_code_idx", columnList = "scope_code")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_SCOPE)
public class SecurityClientScope extends BaseJpaAggregate {

    @Serial
    private static final long serialVersionUID = -7331702188588004616L;

    @Column(name = "scope_code", length = 128, unique = true)
    private String scopeCode;

    @Column(name = "scope_name", length = 128)
    private String scopeName;

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = SecurityClientConstants.REGION_CLIENT_PERMISSION)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "security_client_scope_permission",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"scopeId", "permissionId"})},
            indexes = {@Index(name = "security_client_permission_sid_idx", columnList = "scopeId"), @Index(name = "security_client_permission_pid_idx", columnList = "permissionId")})
    private Set<SecurityClientPermission> permissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SecurityClientScope that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equal(getScopeCode(), that.getScopeCode()) && Objects.equal(getScopeName(), that.getScopeName()) && Objects.equal(getPermissions(), that.getPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getScopeCode(), getScopeName(), getPermissions());
    }
}
