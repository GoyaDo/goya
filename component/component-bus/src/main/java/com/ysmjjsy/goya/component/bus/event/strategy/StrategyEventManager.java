package com.ysmjjsy.goya.component.bus.event.strategy;

import com.ysmjjsy.goya.component.common.json.jackson2.utils.Jackson2Utils;
import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEvent;

/**
 * <p>Description: 策略 Event 定义 </p>
 * <p>
 * 为了同时支持 分布式模式 和 单体式模式。所以很多事件均需要同时支持本地发送和远程发送两种模式。
 * 抽象本定义用于统一处理两种模式的事件支持。
 *
 * @author goya
 * @since 2022/2/5 15:32
 */
public interface StrategyEventManager<T> {

    /**
     * 创建本地事件
     *
     * @param data 事件携带数据
     */
    void postLocalProcess(T data);

    /**
     * 创建远程事件
     *
     * @param data               事件携带数据。JSON 格式的数据。
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    void postRemoteProcess(String data, String originService, String destinationService);

    /**
     * 是否是本地处理事件。
     *
     * @param destinationService 接收远程事件目的地
     * @return false 远程事件，local 本地事件
     */
    default boolean isLocal(String destinationService) {
        return !GoyaContextHolder.getInstance().isDistributedArchitecture() || StringUtils.equals(GoyaContextHolder.getInstance().getApplicationName(), destinationService);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String destinationService, T data) {
        postProcess(GoyaContextHolder.getInstance().getOriginService(), destinationService, data);
    }

    /**
     * 发送事件
     *
     * @param data               事件携带数据
     * @param originService      发送远程事件原始服务
     * @param destinationService 接收远程事件目的地
     */
    default void postProcess(String originService, String destinationService, T data) {
        if (isLocal(destinationService)) {
            postLocalProcess(data);
        } else {
            postRemoteProcess(Jackson2Utils.toJson(data), originService, destinationService);
        }
    }

    /**
     * 发送 Spring Event
     *
     * @param event 自定义 Spring Event
     */
    default void publishEvent(ApplicationEvent event) {
        GoyaContextHolder.getInstance().publishEvent(event);
    }
}
