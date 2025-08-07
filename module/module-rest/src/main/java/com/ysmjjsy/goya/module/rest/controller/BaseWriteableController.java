package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.web.annotation.Idempotent;
import com.ysmjjsy.goya.module.rest.service.BaseWriteableService;
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

/**
 * <p> Description : BaseRestController </p>
 *
 * @author goya
 * @since 2020/2/29 15:28
 */
public abstract class BaseWriteableController<E extends BaseDbEntity, R extends GoyaRepository<E>, S extends BaseWriteableService<E, R>> extends BaseReadableController<E, R, S> {

    @Idempotent
    @Operation(summary = "保存或更新数据", description = "接收JSON数据，转换为实体，进行保存或更新",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "已保存数据", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "domain", required = true, description = "可转换为实体的json数据")
    })
    @PostMapping
    public Response<E> saveOrUpdate(@RequestBody E domain) {
        return result(getService().saveOrUpdate(domain));
    }

    @Idempotent
    @Operation(summary = "删除数据", description = "根据实体ID删除数据，以及相关联的关联数据",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(description = "操作消息", content = @Content(mediaType = "application/json"))})
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.PATH, description = "实体ID，@Id注解对应的实体属性")
    })
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable String id) {
        getService().deleteById(id);
        return Response.success();
    }
}
