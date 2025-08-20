package com.ysmjjsy.goya.security.ext.repository;

import com.ysmjjsy.goya.security.ext.entity.SecurityAuthorizationConsent;
import com.ysmjjsy.goya.security.ext.generator.GoyaAuthorizationConsentId;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:42
 */
public interface SecurityAuthorizationConsentRepository extends JpaRepository<SecurityAuthorizationConsent, GoyaAuthorizationConsentId> {

    /**
     * 根据 client id 和 principalName 查询 OAuth2 确认信息
     *
     * @param registeredClientId 注册OAuth2客户端ID
     * @param principalName      用户名
     * @return OAuth2 认证确认信息 {@link SecurityAuthorizationConsent}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);

    /**
     * 根据 client id 和 principalName 删除 OAuth2 确认信息
     *
     * @param registeredClientId 注册OAuth2客户端ID
     * @param principalName      用户名
     */
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
