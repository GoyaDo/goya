package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.common.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.common.pojo.domain.PageVO;
import com.ysmjjsy.goya.component.common.pojo.response.Response;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.component.db.adapter.GoyaRepository;
import com.ysmjjsy.goya.component.db.domain.BaseDbEntity;
import com.ysmjjsy.goya.component.secure.annotation.AccessLimited;
import com.ysmjjsy.goya.component.web.domain.Controller;
import com.ysmjjsy.goya.module.rest.service.BaseReadableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.core.GenericTypeResolver;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>Description: 只读RestController </p>
 *
 * @author goya
 * @since 2021/7/5 17:21
 */
@SecurityRequirement(name = GoyaConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME)
public abstract class BaseReadableController<E extends BaseDbEntity, R extends GoyaRepository<E>, S extends BaseReadableService<E, R>> implements Controller {

    protected S getService() {
        Class<?>[] typeArguments = GenericTypeResolver.resolveTypeArguments(getClass(), BaseReadableController.class);
        if (typeArguments == null || typeArguments.length < 3) {
            throw new IllegalStateException("无法解析 Service 泛型类型参数，请确保实现类直接继承 BaseReadableService<E, R>");
        }

        Class<S> serviceClass = (Class<S>) typeArguments[2];
        return SpringContextHolder.getBean(serviceClass);
    }

    @AccessLimited
    @Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据",
            responses = {
                    @ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PageVO.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @Parameters({
            @Parameter(name = "pager", required = true, in = ParameterIn.QUERY, description = "分页Bo对象", schema = @Schema(implementation = PageQuery.class))
    })
    @GetMapping
    public Response<PageVO<E>> findByPage(@Validated PageQuery pager) {
        return result(getService().findPage(pager));
    }

    @AccessLimited
    @Operation(summary = "查询详情", description = "通过id查询详情")
    @Parameters({
            @Parameter(name = "id", required = true, in = ParameterIn.QUERY, description = "id", schema = @Schema(implementation = String.class))
    })
    @GetMapping("/{id}")
    public Response<E> findById(@PathVariable String id) {
        return result(getService().findById(id));
    }
}
