package com.ysmjjsy.goya.module.tenant.properties;

import com.ysmjjsy.goya.component.db.constants.DataConstants;
import com.ysmjjsy.goya.module.tenant.enums.MultiTenantApproach;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 自定义 JPA 配置 </p>
 *
 * @author goya
 * @since 2022/9/8 18:22
 */
@Setter
@Getter
@ConfigurationProperties(prefix = DataConstants.PROPERTY_PREFIX_MULTI_TENANT)
public class MultiTenantProperties {

    /**
     * 多租户数据源扫描包
     */
    private String[] packageToScan = new String[]{"com.ysmjjsy.goya"};

    /**
     * 多租户模式，默认：discriminator
     */
    private MultiTenantApproach approach = MultiTenantApproach.DISCRIMINATOR;

}
