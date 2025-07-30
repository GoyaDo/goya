package com.ysmjjsy.goya.component.domain.dispatcher;

import com.ysmjjsy.goya.component.domain.event.DomainEventQueue;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:26
 */
public interface DomainEventDispatcher {

    void dispatchNow(DomainEventQueue queue);
}
