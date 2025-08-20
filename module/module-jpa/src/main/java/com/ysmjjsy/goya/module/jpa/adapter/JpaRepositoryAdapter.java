package com.ysmjjsy.goya.module.jpa.adapter;

import com.ysmjjsy.goya.component.common.exception.transaction.TransactionalRollbackException;
import com.ysmjjsy.goya.component.common.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.common.pojo.domain.PageVO;
import com.ysmjjsy.goya.component.db.adapter.BaseRepositoryAdapter;
import com.ysmjjsy.goya.module.jpa.domain.BaseJpaEntity;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * JPA 仓储适配器
 * 统一实现 BaseRepositoryAdapter 接口
 */
@NoRepositoryBean
public class JpaRepositoryAdapter<E extends BaseJpaEntity>
        extends SimpleJpaRepository<E, String>
        implements BaseRepositoryAdapter<E> {

    // 白名单排序字段，可根据实体字段调整
    private static final Set<String> SORT_FIELD_WHITELIST = new HashSet<>();

    static {
        SORT_FIELD_WHITELIST.add("createdTime");
        SORT_FIELD_WHITELIST.add("updatedTime");
        SORT_FIELD_WHITELIST.add("id");
        // 根据需要添加其他常用排序字段
    }

    public JpaRepositoryAdapter(JpaEntityInformation<E, String> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    /**
     * 保存或更新实体
     */
    @Override
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public E saveOrUpdate(@NotNull E entity) {
       return super.saveAndFlush(entity);
    }

    /**
     * 删除单条记录
     */
    @Override
    @Transactional(rollbackFor = TransactionalRollbackException.class)
    public void removeById(@NotNull String id) {
        super.deleteById(id);
    }

    /**
     * 分页查询
     */
    @Override
    public PageVO<E> findPage(PageQuery pageQuery) {
        int pageIndex = Math.max(pageQuery.getPageIndex() - 1, 0);

        Sort.Direction direction = Sort.Direction.fromOptionalString(pageQuery.getOrderDirection())
                .orElse(Sort.Direction.DESC);

        // 校验排序字段是否在白名单
        String orderBy = StringUtils.isNotBlank(pageQuery.getOrderBy()) &&
                SORT_FIELD_WHITELIST.contains(pageQuery.getOrderBy())
                ? pageQuery.getOrderBy()
                : "createdTime";

        PageRequest pageRequest = PageRequest.of(pageIndex, pageQuery.getPageSize(), direction, orderBy);
        Page<E> pageResult = super.findAll(pageRequest);

        return PageVO.of(
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.getSize(),
                pageResult.getNumber() + 1,
                pageResult.getNumberOfElements(),
                pageResult.getContent()
        );
    }
}