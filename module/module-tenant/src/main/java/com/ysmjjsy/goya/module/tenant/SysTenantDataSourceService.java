//package com.ysmjjsy.goya.module.tenant;
//
//import com.ysmjjsy.goya.module.jpa.tenant.entity.SysTenantDataSource;
//import com.ysmjjsy.goya.module.rest.service.BaseService;
//import org.springframework.stereotype.Service;
//
///**
// * <p>Description: 多租户数据源 </p>
// *
// * @author goya
// * @since 2023/3/29 21:20
// */
//@Service
//public class SysTenantDataSourceService implements BaseService<SysTenantDataSource, String> {
//
//    public SysTenantDataSource findByTenantId(String tenantId) {
//        SysTenantDataSource sysRole = sysTenantDataSourceRepository.findByTenantId(tenantId);
//        log.debug("[Goya] |- SysTenantDataSource Service findByTenantId.");
//        return sysRole;
//    }
//}
