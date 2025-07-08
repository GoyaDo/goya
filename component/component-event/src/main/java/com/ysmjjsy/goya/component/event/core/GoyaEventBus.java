package com.ysmjjsy.goya.component.event.core;

import java.util.concurrent.CompletableFuture;

/**
 * 统一事件总线接口
 * 提供事件发布和订阅的统一入口
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public interface GoyaEventBus {

    /**
     * 发布事件（同步）
     * 
     * @param event 要发布的事件
     * @return 发布结果
     */
    EventPublishResult publish(GoyaEvent event);

    /**
     * 异步发布事件
     * 
     * @param event 要发布的事件
     * @return 异步发布结果
     */
    CompletableFuture<EventPublishResult> publishAsync(GoyaEvent event);

    /**
     * 事务性发布事件
     * 事件将在当前事务提交后发布
     * 
     * @param event 要发布的事件
     * @return 发布结果
     */
    EventPublishResult publishTransactional(GoyaEvent event);

    /**
     * 注册事件监听器
     * 
     * @param listener 事件监听器
     * @param eventType 监听的事件类型
     */
    <T extends GoyaEvent> void subscribe(GoyaEventListener<T> listener, Class<T> eventType);

    /**
     * 注册本地监听器包装器
     *
     * @param wrapper 监听器包装器
     * @param eventType 事件类型
     */
    <T extends GoyaEvent> void subscribe(LocalEventListenerWrapper wrapper, Class<T> eventType);

    /**
     * 取消注册事件监听器
     * 
     * @param listener 事件监听器
     * @param eventType 监听的事件类型
     */
    <T extends GoyaEvent> void unsubscribe(GoyaEventListener<T> listener, Class<T> eventType);

    /**
     * 关闭事件总线，释放资源
     */
    void shutdown();

    /**
     * 获取事件总线的健康状态
     * 
     * @return 健康状态
     */
    EventBusHealth getHealth();
}