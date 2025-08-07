package com.ysmjjsy.goya.module.rest.service;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.pojo.domain.PageVO;
import org.springframework.core.GenericTypeResolver;

import java.util.List;

/**
 * <p>可读的Service基础接口</p>
 *
 * @author goya
 * @since 2025/6/14 17:59
 */
public interface BaseReadableService<E extends BaseDbEntity, R extends GoyaRepository<E>> {

    default R getRepository() {
        Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(getClass(), BaseReadableService.class);
        if (typeArguments == null || typeArguments.length < 2) {
            throw new IllegalStateException("无法解析 Repository 泛型类型参数，请确保实现类直接继承 BaseReadableService<ID, E, R>");
        }

        Class<R> repositoryClass = (Class<R>) typeArguments[1];
        return ApplicationContextHolder.getBean(repositoryClass);
    }

    /**
     * 根据ID查询数据
     *
     * @param id 数据ID
     * @return 与ID对应的数据，如果不存在则返回空
     */
    default E findById(String id) {
        return getRepository().findById(id).orElse(null);
    }

    /**
     * 数据是否存在
     *
     * @param id 数据ID
     * @return true 存在，false 不存在
     */
    default boolean existsById(String id) {
        return getRepository().existsById(id);
    }

    /**
     * 查询数量
     *
     * @return 数据数量
     */
    default long count() {
        return getRepository().count();
    }

    /**
     * 查询全部数据
     *
     * @return 全部数据列表
     */
    default List<E> findAll() {
        return getRepository().findAll();
    }

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    default PageVO<E> findPage(PageQuery query) {
        return getRepository().findPage(query);
    }

}
