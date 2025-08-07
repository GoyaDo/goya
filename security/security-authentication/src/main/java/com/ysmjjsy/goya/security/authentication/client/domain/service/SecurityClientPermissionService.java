package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientPermission;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Description: OAuth2PermissionService </p>
 *
 * @author goya
 * @since 2022/4/1 13:53
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityClientPermissionService extends BaseService<SecurityClientPermission, String> {

    private final SecurityClientPermissionRepository securityClientPermissionRepository;

    @Override
    public GoyaRepository<SecurityClientPermission, String> getRepository() {
        return securityClientPermissionRepository;
    }
}
