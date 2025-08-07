package com.ysmjjsy.goya.security.authentication.client.domain.service;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authentication.client.domain.entity.SecurityClientProduct;
import com.ysmjjsy.goya.security.authentication.client.domain.repository.SecurityClientProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Description: OAuth2ProductService </p>
 *
 * @author goya
 * @since 2023/5/15 16:33
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityClientProductService extends BaseService<SecurityClientProduct, String> {

    private final SecurityClientProductRepository securityClientProductRepository;

    @Override
    public GoyaRepository<SecurityClientProduct, String> getRepository() {
        return securityClientProductRepository;
    }
}
