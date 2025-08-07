package com.ysmjjsy.goya.module.jpa.adapter;

import com.ysmjjsy.goya.component.db.adapter.BaseRepositoryAdapter;
import com.ysmjjsy.goya.component.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.pojo.domain.PageVO;
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

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:19
 */
@NoRepositoryBean
public class JpaRepositoryAdapter<E extends BaseJpaEntity> extends SimpleJpaRepository<E, String> implements BaseRepositoryAdapter<E> {

    private final EntityManager entityManager;

    public JpaRepositoryAdapter(JpaEntityInformation<E, String> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public E saveOrUpdate(@NotNull E entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void remoteById(@NotNull String id) {
        super.deleteById(id);
    }

    @Override
    public PageVO<E> findPage(PageQuery pageQuery) {
        // JPA 页码从 0 开始
        int pageIndex = Math.max(pageQuery.getPageIndex() - 1, 0);

        // 容错处理排序方向
        Sort.Direction direction = Sort.Direction.fromOptionalString(pageQuery.getOrderDirection())
                .orElse(Sort.Direction.DESC);

        // 默认排序字段
        String orderBy = StringUtils.isNotBlank(pageQuery.getOrderBy())
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
