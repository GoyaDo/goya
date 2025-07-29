package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseJpaAggregate;
import com.ysmjjsy.goya.component.db.domain.BaseReadableService;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.web.annotation.Idempotent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * <p> Description : BaseRestController </p>
 *
 * @author goya
 * @since 2020/2/29 15:28
 */
public abstract class BaseWriteableController<E extends BaseJpaAggregate, ID extends Serializable> extends BaseReadableController<E, ID> implements WriteableController<E, ID> {

    @Override
    public BaseReadableService<E, ID> getReadableService() {
        return this.getWriteableService();
    }

    @Idempotent
    @Operation(summary = "保存或更新数据", description = "接收JSON数据，转换为实体，进行保存或更新",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PostMapping
    @Override
    public Response saveOrUpdate(@RequestBody E domain) {
        return WriteableController.super.saveOrUpdate(domain);
    }

    @Idempotent
    @Operation(summary = "删除数据", description = "根据实体ID删除数据，以及相关联的关联数据",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID，@Id注解对应的实体属性")
    })
    @DeleteMapping("/{id}")
    @Override
    public Response delete(@PathVariable ID id) {
        return WriteableController.super.delete(id);
    }
}
