package com.ysmjjsy.goya.module.mybatisplus.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import com.ysmjjsy.goya.component.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.pojo.domain.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 23:38
 */
public interface BaseMpRepository<E extends BaseMpAggregate, ID extends Serializable> extends BaseMapper<E>, BaseRepository<E, ID> {

    @Override
    default E saveOrUpdate(@NotNull E entity) {
        String id = entity.getId();
        if (StringUtils.isEmpty(id)) {
            insert(entity);
        } else {
            updateById(entity);
        }
        return entity;
    }

    @Override
    default void remoteById(@NotNull ID id) {
        deleteById(id);
    }

    @NotNull
    @Override
    default List<E> findAll() {
        return selectList(null);
    }

    @NotNull
    @Override
    default Optional<E> findById(@NotNull ID id) {
        return Optional.ofNullable(selectById(id));
    }

    @Override
    default long count() {
        return selectCount(null);
    }

    @Override
    default boolean existsById(@NotNull ID id) {
        return selectCount(null) == 1;
    }

    @Override
    default PageVO<E> findPage(PageQuery pageQuery) {
        // 构造分页参数
        Page<E> page = Page.of(pageQuery.getPageIndex(), pageQuery.getPageSize());
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();

        // 排序字段：默认 created_time
        String orderBy = StringUtils.isNotBlank(pageQuery.getOrderBy())
                ? convertToColumnName(pageQuery.getOrderBy())
                : "created_time";

        // 排序方向：默认 DESC
        String direction = StringUtils.isNotBlank(pageQuery.getOrderDirection())
                ? pageQuery.getOrderDirection()
                : PageQuery.DESC;

        if (PageQuery.ASC.equalsIgnoreCase(direction)) {
            queryWrapper.orderByAsc(orderBy);
        } else {
            queryWrapper.orderByDesc(orderBy);
        }

        // 分页查询
        IPage<E> resultPage = selectPage(page, queryWrapper);

        return PageVO.of(
                resultPage.getTotal(),
                (int) resultPage.getPages(),
                (int) resultPage.getSize(),
                (int) resultPage.getCurrent(),
                resultPage.getRecords().size(),
                resultPage.getRecords()
        );
    }

    default String convertToColumnName(String fieldName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }
}
