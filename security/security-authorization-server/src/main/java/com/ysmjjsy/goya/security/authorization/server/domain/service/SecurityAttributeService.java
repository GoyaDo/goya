package com.ysmjjsy.goya.security.authorization.server.domain.service;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.domain.repository.SecurityAttributeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityAttributeService extends BaseService<SecurityAttribute,String> {

    private final SecurityAttributeRepository securityAttributeRepository;

    @Override
    public BaseRepository<SecurityAttribute, String> getRepository() {
        return securityAttributeRepository;
    }

    public List<SecurityAttribute> findAllByServiceId(String serviceId) {
        return securityAttributeRepository.findAllByServiceId(serviceId);
    }
}
