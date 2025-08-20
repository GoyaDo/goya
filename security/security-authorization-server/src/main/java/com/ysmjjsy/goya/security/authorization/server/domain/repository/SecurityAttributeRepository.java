package com.ysmjjsy.goya.security.authorization.server.domain.repository;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaRepository;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.AvailableHints;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:19
 */
public interface SecurityAttributeRepository extends BaseJpaRepository<SecurityAttribute> {

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<SecurityAttribute> findAllByServiceId(String serviceId);
}
