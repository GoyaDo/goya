package com.ysmjjsy.goya.module.tenant;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.module.tenant.entity.SysTenantDataSource;
import com.ysmjjsy.goya.module.tenant.repository.SysTenantDataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 多租户数据源 </p>
 *
 * @author goya
 * @since 2023/3/29 21:20
 */
@Service
public class SysTenantDataSourceService implements BaseService<SysTenantDataSource, String> {

    private static final Logger log = LoggerFactory.getLogger(SysTenantDataSourceService.class);

    private final SysTenantDataSourceRepository sysTenantDataSourceRepository;

    public SysTenantDataSourceService(SysTenantDataSourceRepository sysTenantDataSourceRepository) {
        this.sysTenantDataSourceRepository = sysTenantDataSourceRepository;
    }

    @Override
    public BaseRepository<SysTenantDataSource, String> getRepository() {
        return sysTenantDataSourceRepository;
    }

    public SysTenantDataSource findByTenantId(String tenantId) {
        SysTenantDataSource sysRole = sysTenantDataSourceRepository.findByTenantId(tenantId);
        log.debug("[Goya] |- SysTenantDataSource Service findByTenantId.");
        return sysRole;
    }
}
