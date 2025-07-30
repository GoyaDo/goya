package com.ysmjjsy.goya.component.db.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:19
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseDbEntity, ID> {

    /**
     * 保存或更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    E saveOrUpdate(@NotNull E entity);

    /**
     * 批量保存或更新
     *
     * @param entities 实体列表
     * @return 保存或更新后的实体
     */
    List<E> saveOrUpdateAll(@NotNull Iterable<E> entities);

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    void deleteById(@NotNull ID id);

    /**
     * 查询全部数据
     *
     * @return 全部数据列表
     */
    @NotNull
    List<E> findAll();

    /**
     * 根据ID查询数据
     *
     * @param id 数据ID
     * @return 与ID对应的数据，如果不存在则返回空
     */
    @NotNull
    Optional<E> findById(@NotNull ID id);

    /**
     * 查询数量
     *
     * @return 数据数量
     */
    long count();

    /**
     * 数据是否存在
     *
     * @param id 数据ID
     * @return true 存在，false 不存在
     */
    boolean existsById(@NotNull ID id);
}
