package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseReadableService;
import com.ysmjjsy.goya.component.pojo.response.Response;

import java.io.Serializable;

/**
 * Description : 通用Controller
 * 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。
 *
 * @author goya
 * @since 2025/2/26 11:59
 */
public abstract class BaseController<E extends BaseJpaAggregate, ID extends Serializable> implements WriteableController<E, ID> {

    /**
     * 获取Service
     *
     * @return Service
     */
    @Override
    public BaseReadableService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Override
    public Response saveOrUpdate(E domain) {
        E savedDomain = getWriteableService().saveAndFlush(domain);
        return result(savedDomain);
    }

    @Override
    public Response delete(ID id) {
        Response result = result(String.valueOf(id));
        getWriteableService().deleteById(id);
        return result;
    }
}