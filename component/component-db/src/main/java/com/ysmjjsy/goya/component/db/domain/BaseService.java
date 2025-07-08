package com.ysmjjsy.goya.component.db.domain;

import java.io.Serializable;

/**
 * <p>通用核心 Service</p>
 *
 * @author goya
 * @since 2025/6/14 17:57
 */
public interface BaseService<E extends BaseJpaAggregate, ID extends Serializable> extends BaseWriteableService<E, ID> {

}
