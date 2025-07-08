package com.ysmjjsy.goya.component.event.core;

/**
 * 统一事件监听器接口
 * 
 * @param <T> 监听的事件类型
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@FunctionalInterface
public interface GoyaEventListener<T extends GoyaEvent> {

    /**
     * 处理事件
     * 
     * @param event 接收到的事件
     * @throws EventHandleException 事件处理异常
     */
    void onEvent(T event) throws EventHandleException;

    /**
     * 获取监听器优先级，数字越小优先级越高
     * 默认优先级为 0
     * 
     * @return 优先级
     */
    default int getPriority() {
        return 0;
    }

    /**
     * 是否支持异步处理
     * 
     * @return true 支持异步处理，false 同步处理
     */
    default boolean isAsync() {
        return false;
    }
}