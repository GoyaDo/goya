package com.ysmjjsy.goya.module.mybatisplus.adapter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.CaseFormat;
import com.ysmjjsy.goya.component.db.adapter.BaseRepositoryAdapter;
import com.ysmjjsy.goya.component.common.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.common.pojo.domain.PageVO;
import com.ysmjjsy.goya.module.mybatisplus.domain.BaseMpEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 23:38
 */
@RequiredArgsConstructor
public class MybatisPlusRepositoryAdapter<E extends BaseMpEntity> implements BaseRepositoryAdapter<E> {

    private final BaseMapper<E> baseMapper;

    @Override
    public E saveOrUpdate(@NotNull E entity) {
        String id = entity.getId();
        if (StringUtils.isEmpty(id)) {
            baseMapper.insert(entity);
        } else {
            baseMapper.updateById(entity);
        }
        return entity;
    }

    @Override
    public void removeById(@NotNull String id) {
        baseMapper.deleteById(id);
    }

    @NotNull
    @Override
    public List<E> findAll() {
        return baseMapper.selectList(null);
    }

    @NotNull
    @Override
    public Optional<E> findById(@NotNull String id) {
        return Optional.ofNullable(baseMapper.selectById(id));
    }

    @Override
    public long count() {
        return baseMapper.selectCount(null);
    }

    @Override
    public boolean existsById(@NotNull String id) {
        return baseMapper.selectCount(Wrappers.<E>lambdaQuery().eq(E::getId, id)) > 0;
    }

    @Override
    public PageVO<E> findPage(PageQuery pageQuery) {
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
        IPage<E> resultPage = baseMapper.selectPage(page, queryWrapper);

        return PageVO.of(
                resultPage.getTotal(),
                (int) resultPage.getPages(),
                (int) resultPage.getSize(),
                (int) resultPage.getCurrent(),
                resultPage.getRecords().size(),
                resultPage.getRecords()
        );
    }

    private String convertToColumnName(String fieldName) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }
}
