package com.ysmjjsy.goya.security.authorization.auditing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;

import java.util.Optional;

/**
 * <p>Description: 基于 Security 的数据库审计用户信息获取 </p>
 *
 * @author goya
 * @since 2024/4/8 16:01
 */
public class SecurityAuditorAware implements AuditorAware<String> {

    private static final Logger log = LoggerFactory.getLogger(SecurityAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {

        SecurityContext context = SecurityContextHolder.getContext();

        return Optional.ofNullable(context)
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(BearerTokenAuthentication.class::isInstance)
                .map(BearerTokenAuthentication.class::cast)
                .map(AbstractOAuth2TokenAuthenticationToken::getPrincipal)
                .filter(OAuth2IntrospectionAuthenticatedPrincipal.class::isInstance)
                .map(OAuth2IntrospectionAuthenticatedPrincipal.class::cast)
                .map(this::getName)
                .or(Optional::empty);
    }

    private String getName(OAuth2IntrospectionAuthenticatedPrincipal principal) {
        String username = principal.getName();
        log.trace("[Goya] |- Current auditor is : [{}]", username);
        return username;
    }
}
