package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.web.domain.Controller;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * <p> Description : Controller基础定义 </p>
 * <p>
 * 这里只在方法上做了泛型，主要是考虑到返回的结果数据可以是各种类型，而不一定受限于某一种类型。
 *
 * @author goya
 * @since 2020/4/29 18:56
 */
public interface JpaController extends Controller {

    /**
     * 数据分页对象转换为统一响应实体
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link BaseJpaAggregate} 子类型
     * @return {@link Response} Map
     */
    default <E extends BaseJpaAggregate> Response result(Page<E> pages) {
        if (null == pages) {
            return Response.failure("查询数据失败！");
        }

        if (CollectionUtils.isNotEmpty(pages.getContent())) {
            return Response.data("查询数据成功！", getPageInfoMap(pages));
        } else {
            return Response.empty("未查询到数据！");
        }
    }

    /**
     * Page 对象转换为 Map
     *
     * @param pages 分页查询结果 {@link Page}
     * @param <E>   {@link BaseJpaAggregate} 子类型
     * @return Map
     */
    default <E extends BaseJpaAggregate> Map<String, Object> getPageInfoMap(Page<E> pages) {
        return getPageInfoMap(pages.getContent(), pages.getTotalPages(), pages.getTotalElements());
    }
}


