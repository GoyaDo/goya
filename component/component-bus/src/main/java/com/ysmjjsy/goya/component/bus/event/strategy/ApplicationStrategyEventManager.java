package com.ysmjjsy.goya.component.bus.event.strategy;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaEvent;

/**
 * <p>Description: 应用策略事件 </p>
 *
 * @author goya
 * @since 2022/3/29 7:26
 */
public interface ApplicationStrategyEventManager<E extends GoyaEvent> extends StrategyEventManager<E> {

    /**
     * 目的服务名称
     *
     * @return 服务名称
     */
    String getDestinationServiceName();

    /**
     * 发送事件
     *
     * @param data 事件携带数据
     */
    default void postProcess(E data) {
        postProcess(getDestinationServiceName(), data);
    }
}
