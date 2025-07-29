package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.component.db.domain.BaseReadableService;
import com.ysmjjsy.goya.component.pojo.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 只读Controller </p>
 * <p>
 * JPA 支持数据库 View的映射，View 只能读取数据，定义该基础只读 Controller，防止在操作 View 时，调用不该使用的方法。
 *
 * @author goya
 * @since 2020/4/30 11:00
 */
public interface ReadableController<E extends BaseJpaAggregate, ID extends Serializable> extends JpaController {

    /**
     * 获取Service
     *
     * @return Service
     */
    BaseReadableService<E, ID> getReadableService();

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码，起始页码 0
     * @param pageSize   每页显示数据条数
     * @return {@link Response}
     */
    default Response findByPage(Integer pageNumber, Integer pageSize) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize);
        return result(pages);
    }

    /**
     * 查询分页数据
     *
     * @param pageNumber 当前页码, 起始页码 0
     * @param pageSize   每页显示的数据条数
     * @param direction  {@link Sort.Direction}
     * @param properties 排序的属性名称
     * @return 分页数据
     */
    default Response findByPage(Integer pageNumber, Integer pageSize, Sort.Direction direction, String... properties) {
        Page<E> pages = getReadableService().findByPage(pageNumber, pageSize, direction, properties);
        return result(pages);
    }

    default Response findAll() {
        List<E> domains = getReadableService().findAll();
        return result(domains);
    }

    default Response findById(ID id) {
        E domain = getReadableService().findById(id);
        return result(domain);
    }
}
