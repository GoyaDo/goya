package com.ysmjjsy.goya.security.authorization.converter;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;

import java.util.HashSet;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 23:11
 */
public class GoyaUserPrincipalConverter {

    public static GoyaUserPrincipal toUserPrincipal(GoyaUser goyaUser) {
        GoyaUserPrincipal userPrincipal = new GoyaUserPrincipal();
        userPrincipal.setId(goyaUser.getUserId());
        userPrincipal.setName(goyaUser.getUsername());
        userPrincipal.setRoles(goyaUser.getRoles());
        userPrincipal.setAvatar(goyaUser.getAvatar());
        return userPrincipal;
    }

    public static GoyaUserPrincipal toUserPrincipal(OAuth2AuthenticatedPrincipal oauth2AuthenticatedPrincipal) {
        GoyaUserPrincipal userPrincipal = new GoyaUserPrincipal();
        userPrincipal.setId(oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.OPEN_ID));
        userPrincipal.setName(oauth2AuthenticatedPrincipal.getName());
        List<String> roles = oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.ROLES);
        if (CollectionUtils.isNotEmpty(roles)) {
            userPrincipal.setRoles(new HashSet<>(roles));
        }
        userPrincipal.setAvatar(oauth2AuthenticatedPrincipal.getAttribute(SecurityConstants.AVATAR));
        return userPrincipal;
    }

    public static GoyaUserPrincipal toUserPrincipal(Jwt jwt) {
        GoyaUserPrincipal userPrincipal = new GoyaUserPrincipal();
        userPrincipal.setId(jwt.getClaimAsString(SecurityConstants.OPEN_ID));
        userPrincipal.setName(jwt.getClaimAsString(JwtClaimNames.SUB));
        userPrincipal.setRoles(jwt.getClaim(SecurityConstants.ROLES));
        userPrincipal.setAvatar(jwt.getClaimAsString(SecurityConstants.AVATAR));
        return userPrincipal;
    }
}
