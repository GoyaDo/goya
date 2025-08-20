package com.ysmjjsy.goya.security.ext.service;

import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientProduct;
import com.ysmjjsy.goya.security.ext.repository.SecurityClientProductRepository;
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
public class SecurityClientProductService extends BaseService<SecurityClientProduct, SecurityClientProductRepository> {

}
