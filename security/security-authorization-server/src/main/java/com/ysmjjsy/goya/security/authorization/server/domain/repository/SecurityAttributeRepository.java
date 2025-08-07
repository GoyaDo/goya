package com.ysmjjsy.goya.security.authorization.server.domain.repository;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
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
public interface SecurityAttributeRepository extends GoyaRepository<SecurityAttribute,String> {

    @QueryHints(@QueryHint(name = AvailableHints.HINT_CACHEABLE, value = "true"))
    List<SecurityAttribute> findAllByServiceId(String serviceId);
}
