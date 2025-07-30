package com.ysmjjsy.goya.module.rest.service;

import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.domain.BaseRepository;

/**
 * <p>通用核心 Service</p>
 *
 * @author goya
 * @since 2025/6/14 17:57
 */
public abstract class BaseService<E extends BaseDbEntity, ID, R extends BaseRepository<E, ID>> implements BaseWriteableService<E, ID, R> {

}
