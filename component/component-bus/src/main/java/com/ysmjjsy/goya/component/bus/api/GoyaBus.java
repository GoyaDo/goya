package com.ysmjjsy.goya.component.bus.api;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaEvent;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 11:23
 */
public interface GoyaBus {

    /**
     * 发布事件
     *
     * @param event 事件
     */
    void publish(GoyaEvent event);
}
