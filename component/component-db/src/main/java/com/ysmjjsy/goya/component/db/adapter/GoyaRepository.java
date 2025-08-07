package com.ysmjjsy.goya.component.db.adapter;

import com.google.common.collect.Lists;
import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.component.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.pojo.domain.PageVO;
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
public interface GoyaRepository<E extends BaseDbEntity> extends BaseRepository<E> {

    /**
     * 保存或更新
     *
     * @param entity 实体
     * @return 保存后实体
     */
    E saveOrUpdate(@NotNull E entity);

    /**
     * 批量保存
     *
     * @param entities 实体列表
     * @return 保存后的实体
     */
    default List<E> savedOrUpdateAll(@NotNull Iterable<E> entities) {
        entities.forEach(this::saveOrUpdate);
        return Lists.newArrayList(entities);
    }

    /**
     * 根据ID删除数据
     *
     * @param id 数据对应ID
     */
    void remoteById(@NotNull String id);

    /**
     * 根据ID删除数据
     *
     * @param ids 数据对应ID
     */
    default void remoteByIds(@NotNull Iterable<String> ids) {
        ids.forEach(this::remoteById);
    }

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
    Optional<E> findById(@NotNull String id);

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
    boolean existsById(@NotNull String id);

    /**
     * 分页查询
     *
     * @param pageQuery 查询条件
     * @return 分页数据
     */
    PageVO<E> findPage(PageQuery pageQuery);
}
