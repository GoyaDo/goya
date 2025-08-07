package com.ysmjjsy.goya.module.jpa.domain;

import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/1 10:41
 */
@NoRepositoryBean
public interface BaseJpaRepository<E extends BaseJpaEntity> extends GoyaRepository<E>, JpaRepository<E, String>, JpaSpecificationExecutor<E>, QuerydslPredicateExecutor<E> {
}
