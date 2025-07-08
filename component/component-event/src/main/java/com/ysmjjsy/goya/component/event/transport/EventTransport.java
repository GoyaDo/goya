package com.ysmjjsy.goya.component.event.transport;


import com.ysmjjsy.goya.component.event.core.GoyaEventListener;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;

import java.util.concurrent.CompletableFuture;

/**
 * 事件传输接口
 * 抽象不同消息中间件的传输机制
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public interface EventTransport {

    /**
     * 获取传输类型名称
     * 
     * @return 传输类型
     */
    String getTransportType();

    /**
     * 发送事件到远程
     * 
     * @param event 要发送的事件
     * @param topic 目标主题/队列
     * @return 发送结果
     */
    CompletableFuture<TransportResult> send(GoyaEvent event, String topic);

    /**
     * 订阅远程事件
     * 
     * @param topic 订阅的主题/队列
     * @param listener 事件监听器
     * @param eventType 事件类型
     */
    <T extends GoyaEvent> void subscribe(String topic, GoyaEventListener<T> listener, Class<T> eventType);

    /**
     * 取消订阅
     * 
     * @param topic 主题/队列
     * @param listener 事件监听器
     */
    void unsubscribe(String topic, GoyaEventListener<?> listener);

    /**
     * 启动传输组件
     */
    void start();

    /**
     * 停止传输组件
     */
    void stop();

    /**
     * 检查传输组件健康状态
     * 
     * @return 健康状态
     */
    TransportHealth getHealth();

    /**
     * 是否支持事务
     * 
     * @return 是否支持事务
     */
    default boolean supportsTransaction() {
        return false;
    }

    /**
     * 是否支持消息顺序保证
     * 
     * @return 是否支持顺序
     */
    default boolean supportsOrdering() {
        return false;
    }
}