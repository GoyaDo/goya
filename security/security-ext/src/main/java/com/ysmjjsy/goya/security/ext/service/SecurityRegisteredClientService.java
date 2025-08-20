package com.ysmjjsy.goya.security.ext.service;

import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.ext.entity.SecurityRegisteredClient;
import com.ysmjjsy.goya.security.ext.repository.SecurityRegisteredClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:50
 */
@Slf4j
@Service
public class SecurityRegisteredClientService extends BaseService<SecurityRegisteredClient, SecurityRegisteredClientRepository> {

    public Optional<SecurityRegisteredClient> findByClientId(String clientId) {
        Optional<SecurityRegisteredClient> result = this.getRepository().findByClientId(clientId);
        log.trace("[Goya] |- GoyaRegisteredClient Service findByClientId.");
        return result;
    }
}
