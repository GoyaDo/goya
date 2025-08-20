package com.ysmjjsy.goya.security.authorization.resolver;

import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.module.identity.resolver.BearerTokenResolver;
import com.ysmjjsy.goya.security.core.utils.UserPrincipalConverter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

/**
 * <p>Description: Servlet 环境 JWT Token 手动解析器 </p>
 *
 * @author goya
 * @since 2024/4/8 16:48
 */
public class GoyaServletJwtTokenResolver implements BearerTokenResolver {

    private final JwtDecoder jwtDecoder;

    private static final Logger log = LoggerFactory.getLogger(GoyaServletJwtTokenResolver.class);

    public GoyaServletJwtTokenResolver(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public UserPrincipal resolve(String token) {

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token can not be null");
        }

        Jwt jwt = getJwt(token);
        if (ObjectUtils.isNotEmpty(jwt)) {
            UserPrincipal userPrincipal = UserPrincipalConverter.toUserPrincipal(jwt);
            log.debug("[Goya] |- Resolve JWT token to principal details [{}]", userPrincipal);
            return userPrincipal;
        }

        return null;
    }

    private Jwt getJwt(String token) {
        try {
            return this.jwtDecoder.decode(token);
        } catch (BadJwtException failed) {
            log.error("[Goya] |- Failed to decode since the JWT was invalid");
        } catch (JwtException failed) {
            log.error("[Goya] |- Failed to decode JWT, catch exception", failed);
        }

        return null;
    }
}
