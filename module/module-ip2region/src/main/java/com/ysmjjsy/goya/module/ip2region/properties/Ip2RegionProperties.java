package com.ysmjjsy.goya.module.ip2region.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Ip2Region 配置参数 </p>
 *
 * @author goya
 * @since 2023/10/24 11:39
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.GOYA + ".ip2region")
public class Ip2RegionProperties {

    /**
     * Ip V4 地址数据库位置，默认值：classpath*:/db/ip2region.db
     */
    private String ipV4Resource = "classpath:/db/ip2region.xdb";
    /**
     * Ip V6 地址数据库位置，默认值：classpath:db/ipv6wry.db
     */
    private String ipV6Resource = "classpath:/db/ipv6wry.db";

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipV4Resource", ipV4Resource)
                .add("ipV6Resource", ipV6Resource)
                .toString();
    }
}
