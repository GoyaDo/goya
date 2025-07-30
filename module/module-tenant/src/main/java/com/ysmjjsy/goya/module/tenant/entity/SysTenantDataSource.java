package com.ysmjjsy.goya.module.tenant.entity;

import com.ysmjjsy.goya.component.db.constants.DataConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * <p>Description: 租户数据源管理 </p>
 *
 * @author goya
 * @since 2023/3/28 21:45
 */
@Setter
@Getter
@Schema(title = "多租户数据源管理")
@Entity
@Table(name = "sys_tenant_datasource",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id"})},
        indexes = {@Index(name = "sys_tenant_datasource_id_idx", columnList = "datasource_id")})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = DataConstants.REGION_SYS_TENANT_DATASOURCE)
public class SysTenantDataSource extends BaseJpaAggregate {

    @Schema(name = "租户ID", description = "租户的唯一标识")
    @Column(name = "tenant_id", length = 64, unique = true)
    private String tenantId;

    @Schema(name = "数据库用户名")
    @Column(name = "user_name", length = 100)
    private String username;

    @Schema(name = "数据库密码")
    @Column(name = "password", length = 100)
    private String password;

    @Schema(name = "数据库驱动")
    @Column(name = "driver_class_name", length = 64)
    private String driverClassName;

    @Schema(name = "数据库连接")
    @Column(name = "url", length = 1000)
    private String url;

    @Schema(name = "是否已经初始化", description = "默认值 false")
    private Boolean initialize = false;
}
