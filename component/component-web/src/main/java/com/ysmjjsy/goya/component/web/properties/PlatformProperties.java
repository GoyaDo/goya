package com.ysmjjsy.goya.component.web.properties;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import com.ysmjjsy.goya.component.dto.enums.Architecture;
import com.ysmjjsy.goya.component.dto.enums.Protocol;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 平台服务相关配置 </p>
 *
 * @author goya
 * @since 2019/11/17 15:22
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_PREFIX_PLATFORM)
public class PlatformProperties {

    /**
     * 服务架构
     */
    private Architecture architecture = Architecture.MONOCOQUE;

    /**
     * 接口地址默认采用的Http协议类型
     */
    private Protocol protocol = Protocol.HTTP;

    /**
     * 统一网关服务地址。可以是IP+端口，可以是域名
     */
    private String gatewayServiceUri;
}
