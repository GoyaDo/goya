package com.ysmjjsy.goya.module.rest.controller;

import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import com.ysmjjsy.goya.component.pojo.domain.PageQuery;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.web.annotation.AccessLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>Description: 只读RestController </p>
 *
 * @author goya
 * @since 2021/7/5 17:21
 */
@SecurityRequirement(name = GoyaConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME)
public abstract class BaseReadableController<E extends BaseJpaAggregate, ID extends Serializable> implements ReadableController<E, ID> {

    @AccessLimited
    @Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据",
            responses = {
                    @ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            })
    @Parameters({
            @Parameter(name = "pager", required = true, in = ParameterIn.QUERY, description = "分页Bo对象", schema = @Schema(implementation = PageQuery.class))
    })
    @GetMapping
    public Response findByPage(@Validated PageQuery pager) {
        if (StringUtils.isNotEmpty(pager.getOrderBy())) {
            Sort.Direction direction = Sort.Direction.valueOf(pager.getOrderDirection());
            return ReadableController.super.findByPage(pager.getPageIndex(), pager.getPageSize(), direction, pager.getOrderBy());
        } else {
            return ReadableController.super.findByPage(pager.getPageIndex(), pager.getPageSize());
        }
    }
}
