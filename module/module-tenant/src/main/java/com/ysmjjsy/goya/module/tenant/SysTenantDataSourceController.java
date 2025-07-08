package com.ysmjjsy.goya.module.tenant;

import com.ysmjjsy.goya.component.db.domain.BaseWriteableService;
import com.ysmjjsy.goya.component.dto.response.Response;
import com.ysmjjsy.goya.component.web.annotation.AccessLimited;
import com.ysmjjsy.goya.module.rest.controller.BaseWriteableController;
import com.ysmjjsy.goya.module.tenant.entity.SysTenantDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 多租户数据源接口 </p>
 *
 * @author goya
 * @since 2023/3/29 21:23
 */
@RestController
@RequestMapping("/security/tenant/datasource")
@Tags({
        @Tag(name = "系统安全管理接口"),
        @Tag(name = "多租户数据源接口")
})
@RequiredArgsConstructor
public class SysTenantDataSourceController extends BaseWriteableController<SysTenantDataSource, String> {

    private final SysTenantDataSourceService sysTenantDataSourceService;

    @Override
    public BaseWriteableService<SysTenantDataSource, String> getWriteableService() {
        return sysTenantDataSourceService;
    }

    @AccessLimited
    @Operation(summary = "根据租户代码查询数据源", description = "根据输入的租户代码，查询对应的数据源",
            responses = {
                    @ApiResponse(description = "查询到的数据源", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SysTenantDataSource.class))),
                    @ApiResponse(responseCode = "204", description = "查询成功，未查到数据"),
                    @ApiResponse(responseCode = "500", description = "查询失败")
            }
    )
    @Parameters({
            @Parameter(name = "tenantId", in = ParameterIn.PATH, required = true, description = "租户代码"),
    })
    @GetMapping("/{tenantId}")
    public Response<SysTenantDataSource> findByRoleCode(@PathVariable("tenantId") String tenantId) {
        SysTenantDataSource sysTenantDataSource = sysTenantDataSourceService.findByTenantId(tenantId);
        return result(sysTenantDataSource);
    }
}
