package com.ysmjjsy.goya.module.jpa.domain;

import com.ysmjjsy.goya.component.db.domain.BaseRepository;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 17:19
 */
@NoRepositoryBean
public interface BaseJpaRepository<E extends BaseJpaAggregate, ID> extends BaseRepository<E, ID>, JpaRepository<E, ID>, JpaSpecificationExecutor<E>, QuerydslPredicateExecutor<E> {

    @Override
   default E saveOrUpdate(@NotNull E entity){
        return saveAndFlush(entity);
    }

    @Override
   default List<E> saveOrUpdateAll(@NotNull Iterable<E> entities){
        return saveAllAndFlush(entities);
    }

    @Override
    boolean existsById(@NotNull ID id);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll();

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(@NotNull Sort sort);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    Optional<E> findOne(@NotNull Specification<E> specification);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(Specification<E> specification);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    Page<E> findAll(Specification<E> specification, @NotNull Pageable pageable);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    List<E> findAll(Specification<E> specification, @NotNull Sort sort);

    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    long count(Specification<E> specification);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    Page<E> findAll(@NotNull Pageable pageable);

    @NotNull
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    Optional<E> findById(@NotNull ID id);

    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    @Override
    long count();

    @Transactional
    @Override
    void deleteById(@NotNull ID id);

}
