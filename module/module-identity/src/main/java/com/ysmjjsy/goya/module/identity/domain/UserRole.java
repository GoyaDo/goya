package com.ysmjjsy.goya.module.identity.domain;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.common.pojo.domain.DTO;

import java.util.Set;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 17:58
 */
public record UserRole(
        /*
           角色ID
         */
        String roleId,

        /*
           角色编码
         */
        String roleCode,

        /*
           角色名称
         */
        String roleName,

        /*
           角色权限
         */
        Set<RolePermission> permissions
) implements DTO {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return Objects.equal(roleId(), userRole.roleId()) && Objects.equal(roleCode(), userRole.roleCode()) && Objects.equal(roleName(), userRole.roleName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleId(), roleCode(), roleName());
    }
}
