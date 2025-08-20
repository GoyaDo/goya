package com.ysmjjsy.goya.security.core.service;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:51
 */
public interface EnhanceOAuth2AuthorizationService extends OAuth2AuthorizationService {

    /**
     * 查询某个用户的认证（Token）数据量
     *
     * @param registeredClientId 客户端ID
     * @param principalName      用户唯一标识
     * @return 数量
     */
    int findAuthorizationCount(String registeredClientId, String principalName);

    /**
     * 选项用户当前有效的所有认证（Token）
     *
     * @param registeredClientId 客户端ID
     * @param principalName      用户唯一标识
     * @return 有效的认证（Token） 列表
     */
    List<OAuth2Authorization> findAvailableAuthorizations(String registeredClientId, String principalName);
}
