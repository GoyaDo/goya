package com.ysmjjsy.goya.component.web.initializer;

import com.ysmjjsy.goya.component.common.utils.WellFormedUtils;
import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import com.ysmjjsy.goya.component.web.properties.PlatformProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;

/**
 * <p>Description: ServiceContextHolder 构建器 </p>
 *
 * @author goya
 * @since 2024/1/24 17:24
 */
public class GoyaContextHolderBuilder {

    private static final Logger log = LoggerFactory.getLogger(GoyaContextHolderBuilder.class);

    private PlatformProperties platformProperties;
    private ServerProperties serverProperties;

    private GoyaContextHolderBuilder() {

    }

    public static GoyaContextHolderBuilder builder() {
        return new GoyaContextHolderBuilder();
    }

    public GoyaContextHolderBuilder platformProperties(PlatformProperties platformProperties) {
        this.platformProperties = platformProperties;
        return this;
    }

    public GoyaContextHolderBuilder serverProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
        return this;
    }

    public GoyaContextHolder build() {
        GoyaContextHolder holder = GoyaContextHolder.getInstance();
        holder.setPort(String.valueOf(this.getPort()));
        holder.setIp(getHostAddress());
        setProperties(platformProperties, holder);
        return holder;
    }

    private String getHostAddress() {
        String address = WellFormedUtils.getHostAddress();
        if (ObjectUtils.isNotEmpty(serverProperties.getAddress())) {
            address = serverProperties.getAddress().getHostAddress();
        }

        if (StringUtils.isNotBlank(address)) {
            return address;
        } else {
            return "localhost";
        }
    }

    private Integer getPort() {
        Integer port = serverProperties.getPort();
        if (ObjectUtils.isNotEmpty(port)) {
            return port;
        } else {
            return 8080;
        }
    }

    private void setProperties(PlatformProperties platformProperties, GoyaContextHolder serviceContextHolder) {
        serviceContextHolder.setArchitecture(platformProperties.getArchitecture());
        serviceContextHolder.setProtocol(platformProperties.getProtocol());
        serviceContextHolder.setGatewayServiceUri(platformProperties.getGatewayServiceUri());
        serviceContextHolder.setAuthServiceUri(platformProperties.getAuthServiceUri());
        serviceContextHolder.setAuthServiceName(platformProperties.getAuthServiceName());
    }
}
