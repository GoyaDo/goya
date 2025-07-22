package com.ysmjjsy.goya.security.authentication.client.domain.repository;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:53
 */
public interface SecurityClientScopeRepository extends BaseRepository<SecurityClientScope, String> {

    /**
     * 根据范围代码查询应用范围
     *
     * @param scopeCode 范围代码
     * @return 应用范围 {@link SecurityClientScope}
     */
    SecurityClientScope findByScopeCode(String scopeCode);

    /**
     * 根据 scope codes 查询对应的对象列表
     *
     * @param scopeCodes 范围代码
     * @return 对象列表
     */
    List<SecurityClientScope> findByScopeCodeIn(List<String> scopeCodes);
}
