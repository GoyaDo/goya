package com.ysmjjsy.goya.security.authentication.client.domain.repository;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaRepository;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientApplication;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:50
 */
public interface SecurityClientApplicationRepository extends BaseJpaRepository<SecurityClientApplication> {

    /**
     * 根据 Client ID 查询 SecurityClientApplication
     *
     * @param clientId SecurityClientApplication 中的 clientId
     * @return {@link SecurityClientApplication}
     */
    SecurityClientApplication findByClientId(String clientId);
}
