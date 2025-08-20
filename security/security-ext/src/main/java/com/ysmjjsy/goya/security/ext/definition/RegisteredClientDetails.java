package com.ysmjjsy.goya.security.ext.definition;

import java.time.LocalDateTime;

/**
 * <p>RegisteredClient 属性定义</p>
 *
 * @author goya
 * @since 2025/7/21 23:25
 */
public interface RegisteredClientDetails {

    String getId();

    String getClientId();

    LocalDateTime getClientIdIssuedAt();

    String getClientSecret();

    LocalDateTime getClientSecretExpiresAt();

    String getClientAuthenticationMethods();

    String getAuthorizationGrantTypes();

    String getRedirectUris();

    String getPostLogoutRedirectUris();
}
