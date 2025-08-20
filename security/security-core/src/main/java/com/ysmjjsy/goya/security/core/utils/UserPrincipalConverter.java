package com.ysmjjsy.goya.security.core.utils;

import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.List;

/**
 * <p>TODO</p>
 *
 * @author goya
 * @since 2025/8/19 21:48
 */
@UtilityClass
public class UserPrincipalConverter {

    public static UserPrincipal toUserPrincipal(GoyaUser goyaUser) {
        return new UserPrincipal(
                goyaUser.getUserId(),
                goyaUser.getUsername(),
                null,
                null,
                null,
                goyaUser.getAvatar(),
                goyaUser.getPassword(),
                null,
                null,
                null,
                null
        );
    }

    public static UserPrincipal toUserPrincipal(OAuth2AuthenticatedPrincipal oauth2AuthenticatedPrincipal) {
        List<String> roles = oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.ROLES);
//        if (CollectionUtils.isNotEmpty(roles)) {
//            userPrincipal.setRoles(new HashSet<>(roles));
//        }
        return new UserPrincipal(
                oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.OPEN_ID),
                oauth2AuthenticatedPrincipal.getName(),
                null,
                null,
                null,
                oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.AVATAR),
                null,
                null,
                null,
                null,
                null
        );
    }

    public static UserPrincipal toUserPrincipal(Jwt jwt) {
        Object claim = jwt.getClaim(SecurityConstants.ROLES);
        return new UserPrincipal(
                jwt.getClaimAsString(SecurityConstants.OPEN_ID),
                jwt.getClaimAsString(JwtClaimNames.SUB),
                null,
                null,
                null,
                jwt.getClaimAsString(SecurityConstants.AVATAR),
                null,
                null,
                null,
                null,
                null
        );
    }
}
