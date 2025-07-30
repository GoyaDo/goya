package com.ysmjjsy.goya.component.bus.api;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 11:23
 */
public interface GoyaBus {

    void publish();

    void publish(GoyaAbstractEvent event);
}
