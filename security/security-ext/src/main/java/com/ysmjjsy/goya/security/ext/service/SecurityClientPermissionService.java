package com.ysmjjsy.goya.security.ext.service;

import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientPermission;
import com.ysmjjsy.goya.security.ext.repository.SecurityClientPermissionRepository;
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
public class SecurityClientPermissionService extends BaseService<SecurityClientPermission, SecurityClientPermissionRepository> {

}
