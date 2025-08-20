package com.ysmjjsy.goya.security.ext.repository;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaRepository;
import com.ysmjjsy.goya.security.ext.entity.SecurityRegisteredClient;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:43
 */
public interface SecurityRegisteredClientRepository extends BaseJpaRepository<SecurityRegisteredClient> {

    /**
     * 根据 ClientId 查询 RegisteredClient
     *
     * @param clientId OAuth2 客户端ID
     * @return OAuth2 客户端配置
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    Optional<SecurityRegisteredClient> findByClientId(String clientId);
}
