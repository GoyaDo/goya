package com.ysmjjsy.goya.security.ext.repository;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaRepository;
import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorization;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:41
 */
public interface SecurityAuthorizationRepository extends BaseJpaRepository<SecurityAuthorization> {
    /**
     * 根据 State 查询 OAuth2 认证信息
     *
     * @param state OAuth2 Authorization Code 模式参数 State
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByState(String state);

    /**
     * 根据 authorizationCode 查询 OAuth2 认证信息
     *
     * @param authorizationCode OAuth2 Authorization Code 模式参数 code
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByAuthorizationCodeValue(String authorizationCode);

    /**
     * 根据 Access Token 查询 OAuth2 认证信息
     *
     * @param accessToken OAuth2 accessToken
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByAccessTokenValue(String accessToken);

    /**
     * 根据 Refresh Token 查询 OAuth2 认证信息
     *
     * @param refreshToken OAuth2 refreshToken
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByRefreshTokenValue(String refreshToken);

    /**
     * 根据 Id Token 查询 OAuth2 认证信息
     *
     * @param idToken OAuth2 idToken
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByOidcIdTokenValue(String idToken);

    /**
     * 根据 User Code 查询 OAuth2 认证信息
     *
     * @param userCode OAuth2 userCode
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByUserCodeValue(String userCode);

    /**
     * 根据 Device Code 查询 OAuth2 认证信息
     *
     * @param deviceCode OAuth2 deviceCode
     * @return OAuth2 认证信息 {@link SecurityAuthorization}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorization> findByDeviceCodeValue(String deviceCode);

    /**
     * 根据客户端ID和用户名查询未过期Token
     *
     * @param registeredClientId 客户端ID
     * @param principalName      用户名称
     * @param localDateTime      时间
     * @return 认证信息列表
     */
    List<SecurityAuthorization> findAllByRegisteredClientIdAndPrincipalNameAndAccessTokenExpiresAtAfter(String registeredClientId, String principalName, LocalDateTime localDateTime);

    /**
     * 根据 RefreshToken 过期时间，清理历史 Token信息
     * <p>
     * OAuth2Authorization 表中存在 AccessToken、OidcToken、RefreshToken 等三个过期时间。
     * 正常的删除逻辑应该是三个过期时间都已经过期才行。但是有特殊情况：
     * 1. OidcToken 的过期时间有可能为空，这就增加了 SQL 处理的复杂度。
     * 2. 逻辑上 RefreshToken 的过期应该是最长的(这是默认配置正确的情况)
     * 因此，目前就简单的根据 RefreshToken过期时间进行处理
     *
     * @param localDateTime 时间
     */
    @Modifying
    @Transactional
    void deleteByRefreshTokenExpiresAtBefore(LocalDateTime localDateTime);
}
