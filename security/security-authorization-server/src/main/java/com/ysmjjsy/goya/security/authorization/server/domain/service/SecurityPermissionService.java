package com.ysmjjsy.goya.security.authorization.server.domain.service;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityPermission;
import com.ysmjjsy.goya.security.authorization.server.domain.repository.SecurityPermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityPermissionService extends BaseService<SecurityPermission,String> {

    private final SecurityPermissionRepository securityPermissionRepository;

    @Override
    public GoyaRepository<SecurityPermission, String> getRepository() {
        return securityPermissionRepository;
    }
}
