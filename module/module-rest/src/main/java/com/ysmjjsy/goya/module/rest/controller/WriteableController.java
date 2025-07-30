package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseWriteableService;
import com.ysmjjsy.goya.component.pojo.response.Response;

import java.io.Serializable;

/**
 * <p> Description : 可写Controller </p>
 *
 * @author goya
 * @since 2020/4/30 11:00
 */
public interface WriteableController<E extends BaseJpaAggregate, ID extends Serializable> extends ReadableController<E, ID> {

    /**
     * 获取Service
     *
     * @return Service
     */
    BaseWriteableService<E, ID> getWriteableService();

    /**
     * 保存或更新实体
     *
     * @param domain 实体参数
     * @return 用Result包装的实体
     */

    default Response saveOrUpdate(E domain) {
        E savedDomain = getWriteableService().saveAndFlush(domain);
        return result(savedDomain);
    }

    /**
     * 删除数据
     *
     * @param id 实体ID
     * @return 用Result包装的信息
     */
    default Response delete(ID id) {
        Response result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}
