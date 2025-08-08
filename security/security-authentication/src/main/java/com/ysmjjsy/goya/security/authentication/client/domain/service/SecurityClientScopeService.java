package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientPermission;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientScope;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientScopeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p> Description : OauthScopeService </p>
 *
 * @author goya
 * @since 2020/3/19 17:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityClientScopeService extends BaseService<SecurityClientScope, SecurityClientScopeRepository> {

    private final SecurityClientScopeRepository securityClientScopeRepository;

    public SecurityClientScope assigned(String scopeId, Set<SecurityClientPermission> permissions) {

        SecurityClientScope oldScope = findById(scopeId);
        oldScope.setPermissions(permissions);

        return getRepository().saveAndFlush(oldScope);
    }

    public SecurityClientScope findByScopeCode(String scopeCode) {
        return securityClientScopeRepository.findByScopeCode(scopeCode);
    }

    public List<SecurityClientScope> findByScopeCodeIn(List<String> scopeCodes) {
        return securityClientScopeRepository.findByScopeCodeIn(scopeCodes);
    }
}
