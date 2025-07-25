package com.ysmjjsy.goya.module.tenant.repository;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.module.tenant.entity.SysTenantDataSource;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

/**
 * <p>Description: 多租户数据源 Repository </p>
 *
 * @author goya
 * @since 2023/3/28 21:58
 */
public interface SysTenantDataSourceRepository extends BaseRepository<SysTenantDataSource, String> {

    /**
     * 根据租户ID查询数据源
     *
     * @param tenantId 租户ID
     * @return {@link SysTenantDataSource}
     */
    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    SysTenantDataSource findByTenantId(String tenantId);
}
