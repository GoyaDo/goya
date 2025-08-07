package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.module.rest.service.BaseWriteableService;

/**
 * Description : 通用Controller
 * 单独提取出一些公共方法，是为了解决某些支持feign的controller，requestMapping 不方便统一编写的问题。
 *
 * @author goya
 * @since 2025/2/26 11:59
 */
public abstract class BaseController<E extends BaseDbEntity, R extends GoyaRepository<E>, S extends BaseWriteableService<E, R>> extends BaseWriteableController<E, R, S> {

}