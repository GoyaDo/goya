package com.ysmjjsy.goya.security.authentication.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>Description: OAuth2 (Security) 表单登录 Token </p>
 *
 * @author goya
 * @since 2022/4/12 10:24
 * @see UsernamePasswordAuthenticationToken
 */
public class OAuth2FormLoginAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public OAuth2FormLoginAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
