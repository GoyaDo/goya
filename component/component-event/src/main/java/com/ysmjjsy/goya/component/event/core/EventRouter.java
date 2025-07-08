package com.ysmjjsy.goya.component.event.core;

/**
 * 事件路由器接口
 * 决定事件的路由策略和目标
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public interface EventRouter {

    /**
     * 路由事件，决定处理策略
     * 
     * @param event 要路由的事件
     * @return 路由决策
     */
    EventRoutingDecision route(GoyaEvent event);
}