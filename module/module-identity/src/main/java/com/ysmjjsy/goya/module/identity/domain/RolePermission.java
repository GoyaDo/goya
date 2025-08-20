package com.ysmjjsy.goya.module.identity.domain;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.common.pojo.domain.DTO;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 18:03
 */
public record RolePermission(
        /*
          权限Id
         */
        String permissionId,

        /*
          权限编码
         */
        String permissionCode,

        /*
          权限名称
         */
        String permissionName
) implements DTO {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equal(permissionId(), that.permissionId()) && Objects.equal(permissionCode(), that.permissionCode()) && Objects.equal(permissionName(), that.permissionName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(permissionId(), permissionCode(), permissionName());
    }
}
