package com.ysmjjsy.goya.security.authentication.customizer;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: TokenCustomizer 通用处理 </p>
 *
 * @author goya
 * @since 2022/10/12 10:20
 */
public abstract class AbstractTokenCustomizer {

    protected void appendAll(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {

        appendAuthorities(attributes, authentication);
        appendCommons(attributes, authentication, authorizedScopes);

    }

    protected void appendAuthorities(Map<String, Object> attributes, Authentication authentication) {

        if (CollectionUtils.isNotEmpty(authentication.getAuthorities())) {
            Set<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            attributes.put(SecurityConstants.AUTHORITIES, authorities);
        }
    }

    protected void appendCommons(Map<String, Object> attributes, Authentication authentication, Set<String> authorizedScopes) {

        if (CollectionUtils.isNotEmpty(authorizedScopes)) {
            attributes.put(OAuth2ParameterNames.SCOPE, authorizedScopes);
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            putUserInfo(attributes, authentication.getPrincipal());
        }

        if (authentication instanceof OAuth2AccessTokenAuthenticationToken) {
            Object details = authentication.getDetails();
            putUserInfo(attributes, details);
        }

        attributes.put("license", GoyaConstants.LICENSE);
    }

    private void putUserInfo(Map<String, Object> attributes, Object object) {
        if (ObjectUtils.isNotEmpty(object) && object instanceof GoyaUser principal) {
            attributes.put(SecurityConstants.OPEN_ID, principal.getUserId());
            attributes.put(SecurityConstants.ROLES, principal.getRoles());
            attributes.put(SecurityConstants.AVATAR, principal.getAvatar());
        }
    }
}
