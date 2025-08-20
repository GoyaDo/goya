package com.ysmjjsy.goya.security.core.service.converter;

import com.ysmjjsy.goya.module.identity.domain.RolePermission;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.module.identity.domain.UserRole;
import com.ysmjjsy.goya.module.identity.user.AbstractUserConverter;
import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import com.ysmjjsy.goya.security.core.utils.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 17:55
 */
public class UserToGoyaUserConverter extends AbstractUserConverter<GoyaUser> {

    @Override
    public GoyaUser convert(UserPrincipal source) {
        Set<GoyaGrantedAuthority> authorities = new HashSet<>();

        Set<String> roles = new HashSet<>();
        for (UserRole role : source.roles()) {
            roles.add(role.roleCode());
            authorities.add(new GoyaGrantedAuthority(SecurityUtils.wellFormRolePrefix(role.roleCode())));
            Set<RolePermission> sysPermissions = role.permissions();
            if (CollectionUtils.isNotEmpty(sysPermissions)) {
                sysPermissions.forEach(sysAuthority -> authorities.add(new GoyaGrantedAuthority((sysAuthority.permissionCode()))));
            }
        }

        return new GoyaUser(source.getUserId(), source.username(), source.password(),
                isEnabled(source),
                isAccountNonExpired(source),
                isCredentialsNonExpired(source),
                isAccountNonLocked(source),
                authorities, roles, source.getAvatar());
    }
}
