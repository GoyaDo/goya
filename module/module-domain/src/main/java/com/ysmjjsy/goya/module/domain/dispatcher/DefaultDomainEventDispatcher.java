package com.ysmjjsy.goya.module.domain.dispatcher;

import com.ysmjjsy.goya.component.bus.api.GoyaBus;
import com.ysmjjsy.goya.module.domain.event.DomainEventQueue;
import lombok.RequiredArgsConstructor;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:27
 */
@RequiredArgsConstructor
public class DefaultDomainEventDispatcher implements DomainEventDispatcher {

    private final GoyaBus goyaBus;

    @Override
    public void dispatchNow(DomainEventQueue queue) {
        queue.queue().forEach(goyaBus::publish);
    }
}
