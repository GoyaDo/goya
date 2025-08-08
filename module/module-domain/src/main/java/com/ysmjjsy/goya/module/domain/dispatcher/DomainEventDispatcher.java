package com.ysmjjsy.goya.module.domain.dispatcher;

import com.ysmjjsy.goya.module.domain.event.DomainEventQueue;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:26
 */
public interface DomainEventDispatcher {

    void dispatchNow(DomainEventQueue queue);
}
