package com.ysmjjsy.goya.security.authorization.resolver;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import com.ysmjjsy.goya.module.identity.resolver.BearerTokenResolver;
import com.ysmjjsy.goya.security.authorization.converter.GoyaUserPrincipalConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.BadOpaqueTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * <p>Description: Servlet 环境 Opaque Token 手动解析器 </p>
 *
 * @author goya
 * @since 2024/4/8 16:38
 */
public class GoyaServletOpaqueTokenResolver implements BearerTokenResolver {

    private static final Logger log = LoggerFactory.getLogger(GoyaServletOpaqueTokenResolver.class);

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    public GoyaServletOpaqueTokenResolver(OpaqueTokenIntrospector opaqueTokenIntrospector) {
        this.opaqueTokenIntrospector = opaqueTokenIntrospector;
    }

    @Override
    public GoyaUserPrincipal resolve(String token) {

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token can not be null");
        }

        OAuth2AuthenticatedPrincipal principal = getOpaque(token);
        if (ObjectUtils.isNotEmpty(principal)) {
            GoyaUserPrincipal userPrincipal = GoyaUserPrincipalConverter.toUserPrincipal(principal);
            log.debug("[Goya] |- Resolve OPAQUE token to principal details [{}]", userPrincipal);
            return userPrincipal;
        }
        return null;
    }

    private OAuth2AuthenticatedPrincipal getOpaque(String token) {
        try {
            return this.opaqueTokenIntrospector.introspect(token);
        } catch (BadOpaqueTokenException failed) {
            log.warn("Failed to introspect since the Opaque was invalid");
        } catch (OAuth2IntrospectionException failed) {
            log.warn("[Goya] |- Failed to introspect Opaque, catch exception", failed);
        }

        return null;
    }
}
