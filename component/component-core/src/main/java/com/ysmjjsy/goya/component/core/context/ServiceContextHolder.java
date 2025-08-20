package com.ysmjjsy.goya.component.core.context;

import com.ysmjjsy.goya.component.common.constants.SymbolConstants;
import com.ysmjjsy.goya.component.common.pojo.enums.Architecture;
import com.ysmjjsy.goya.component.common.pojo.enums.Protocol;
import com.ysmjjsy.goya.component.common.strategy.Singleton;
import com.ysmjjsy.goya.component.core.utils.WellFormedUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * <p>Description: 服务上下文信息工具类 </p>
 *
 * @author goya
 * @since 2022/1/14 17:28
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceContextHolder {

    /**
     * 服务架构
     */
    private Architecture architecture = Architecture.MONOCOQUE;

    /**
     * 协议头类型
     */
    private Protocol protocol = Protocol.HTTP;
    /**
     * 服务端口号
     */
    private String port;
    /**
     * 服务IP地址
     */
    private String ip;
    /**
     * 服务地址，格式：ip:port
     */
    private String address;
    /**
     * 服务Url，格式：http://ip:port
     */
    private String url;
    /**
     * 应用名称，与spring.application.name一致
     */
    private String applicationName;
    /**
     * 留存一份ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * 认证中心服务名称
     */
    private String authServiceName;

    /**
     * 统一认证中心服务地址
     */
    private String authServiceUri;

    /**
     * 统一网关服务地址。可以是IP+端口，可以是域名
     */
    private String gatewayServiceUri;

    public String getAddress() {
        if (isDistributedArchitecture()) {
            this.address = this.getGatewayServiceUri() + SymbolConstants.FORWARD_SLASH + this.getApplicationName();
        } else {
            if (StringUtils.isNotBlank(this.ip) && StringUtils.isNotBlank(this.port)) {
                this.address = this.ip + SymbolConstants.COLON + this.port;
            }
        }
        return address;
    }

    public String getUrl() {
        if (StringUtils.isBlank(this.url)) {
            String address = this.getAddress();
            if (StringUtils.isNotBlank(address)) {
                return WellFormedUtils.addressToUri(address, getProtocol(), false);
            }
        }
        return url;
    }

    public boolean isDistributedArchitecture() {
        return this.getArchitecture() == Architecture.DISTRIBUTED;
    }

    public String getOriginService() {
        return getApplicationName() + SymbolConstants.COLON + SymbolConstants.DOUBLE_STAR;
    }

    public void publishEvent(ApplicationEvent applicationEvent) {
        getApplicationContext().publishEvent(applicationEvent);
    }


    public void publishEvent(Object event) {
        getApplicationContext().publishEvent(event);
    }

    public String getId() {
        return this.getApplicationName() + SymbolConstants.COLON + this.getPort();
    }

    /**
     * 通过给定的 ServiceId 判断是否来自于自身。
     * <p>
     * 主要用于解决在消息队列场景，服务自身既是某个主题的生产者又是该主题消费者。那么在该服务多实例的情况下，很难判断“主从”关系。那么通过这个方法来判断。
     *
     * @param serviceId 格式为 spring.application.name : service.port
     * @return true 来自于服务自己，false 来自于其它服务
     */
    public boolean isFromSelf(String serviceId) {
        if (StringUtils.contains(serviceId, SymbolConstants.COLON)) {
            return StringUtils.equals(serviceId, getId());
        }

        return false;
    }

    public static  ServiceContextHolder getInstance() {
        ServiceContextHolder serviceContextHolder = Singleton.get(ServiceContextHolder.class);
        return Objects.requireNonNullElseGet(serviceContextHolder, ServiceContextHolder::new);
    }
}
