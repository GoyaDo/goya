package com.ysmjjsy.goya.component.event.configuration;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 事件总线配置属性
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.GOYA + ".event")
public class EventBusProperties {

    /**
     * 是否启用事件总线
     */
    private boolean enabled = true;

    /**
     * 默认主题名称
     * 如果不配置，将使用当前服务的applicationName作为默认topic
     */
    private String defaultTopic;

    /**
     * 是否启用监控指标
     */
    private boolean metricsEnabled = true;

    /**
     * 是否启用健康检查
     */
    private boolean healthEnabled = true;

    /**
     * 异步处理配置
     */
    private Async async = new Async();

    /**
     * 自动注册配置
     */
    private AutoRegistration autoRegistration = new AutoRegistration();

    /**
     * 异步处理配置
     */
    @Setter
    @Getter
    public static class Async {
        private boolean enabled = true;
        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 1000;
        private String threadNamePrefix = "eventbus-async-";
    }

    /**
     * 自动注册配置
     */
    @Setter
    @Getter
    public static class AutoRegistration {
        /**
         * 是否启用注解监听器自动注册
         */
        private boolean annotationEnabled = true;

        /**
         * 是否启用接口监听器自动注册
         */
        private boolean interfaceEnabled = true;

        /**
         * 是否在启动时打印注册的监听器信息
         */
        private boolean printRegisteredListeners = true;
    }
}