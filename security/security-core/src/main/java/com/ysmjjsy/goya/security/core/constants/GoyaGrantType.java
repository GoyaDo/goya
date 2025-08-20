package com.ysmjjsy.goya.security.core.constants;

import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * <p>Description: 自定义 Grant Type 类型 </p>
 *
 * @author goya
 * @since 2022/2/25 9:53
 */
public interface GoyaGrantType {

    AuthorizationGrantType SOCIAL = new AuthorizationGrantType(SecurityConstants.SOCIAL_CREDENTIALS);

    AuthorizationGrantType PASSWORD = new AuthorizationGrantType(SecurityConstants.PASSWORD);
}
