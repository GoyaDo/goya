package com.ysmjjsy.goya.security.authorization.converter;

import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * <p>Description: 扩展的 JwtAuthenticationConverter </p>
 *
 * @author goya
 * @since 2022/3/22 11:49
 */
public class GoyaJwtAuthenticationConverter extends JwtAuthenticationConverter {

    public GoyaJwtAuthenticationConverter() {
        GoyaJwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new GoyaJwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.AUTHORITIES);

        this.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    }
}
