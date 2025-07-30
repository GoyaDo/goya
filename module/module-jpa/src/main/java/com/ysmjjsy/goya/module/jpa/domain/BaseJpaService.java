package com.ysmjjsy.goya.module.jpa.domain;

import com.ysmjjsy.goya.component.db.domain.BaseService;

import java.io.Serializable;

/**
 * <p>通用核心 Service</p>
 *
 * @author goya
 * @since 2025/6/14 17:57
 */
public abstract class BaseJpaService<E extends BaseJpaAggregate, ID extends Serializable, R extends BaseJpaRepository<E, ID>> extends BaseService<E, ID, R> implements BaseJpaWriteableService<E, ID, R> {

}
