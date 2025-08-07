package com.ysmjjsy.goya.security.authentication.client.domain.repository;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientApplication;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:50
 */
public interface SecurityClientApplicationRepository extends GoyaRepository<SecurityClientApplication,String> {

    /**
     * 根据 Client ID 查询 SecurityClientApplication
     *
     * @param clientId SecurityClientApplication 中的 clientId
     * @return {@link SecurityClientApplication}
     */
    SecurityClientApplication findByClientId(String clientId);
}
