package com.ysmjjsy.goya.module.rest.service;

import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;

import java.util.List;

/**
 * <p>可读、可写的Service基础接口</p>
 *
 * @author goya
 * @since 2025/6/14 18:00
 */
public interface BaseWriteableService<E extends BaseDbEntity, R extends GoyaRepository<E>> extends BaseReadableService<E, R> {

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    default void deleteById(String id) {
        getRepository().remoteById(id);
    }

    /**
     * 保存或更新数据
     *
     * @param domain 数据对应实体
     * @return 已保存数据
     */
    default E saveOrUpdate(E domain) {
        return getRepository().saveOrUpdate(domain);
    }

    /**
     * 批量保存或更新数据
     *
     * @param entities 实体集合
     * @return 已经保存的实体集合
     */
    default List<E> saveOrUpdateAll(Iterable<E> entities) {
        return getRepository().savedOrUpdateAll(entities);
    }
}
