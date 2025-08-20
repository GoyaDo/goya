package com.ysmjjsy.goya.security.ext.client;

import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;
import com.ysmjjsy.goya.security.core.service.EnhanceClientDetailsService;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientApplication;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientPermission;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.ext.service.SecurityClientApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Description: 客户端交互处理器 </p>
 *
 * @author goya
 * @since 2022/4/1 15:21
 */
@Slf4j
@RequiredArgsConstructor
public class GoyaClientDetailsService implements EnhanceClientDetailsService {

    private final SecurityClientApplicationService securityClientApplicationService;


    @Override
    public Set<GoyaGrantedAuthority> findAuthoritiesById(String clientId) {

        SecurityClientApplication application = securityClientApplicationService.findByClientId(clientId);
        if (ObjectUtils.isNotEmpty(application)) {
            Set<SecurityClientScope> scopes = application.getScopes();
            Set<GoyaGrantedAuthority> result = new HashSet<>();
            if (CollectionUtils.isNotEmpty(scopes)) {
                for (SecurityClientScope scope : scopes) {
                    Set<SecurityClientPermission> permissions = scope.getPermissions();
                    if (CollectionUtils.isNotEmpty(permissions)) {
                        Set<GoyaGrantedAuthority> grantedAuthorities = permissions.stream().map(item -> new GoyaGrantedAuthority(item.getPermissionCode())).collect(Collectors.toSet());
                        result.addAll(grantedAuthorities);
                    }
                }
            }
            return result;
        }

        return new HashSet<>();
    }
}
