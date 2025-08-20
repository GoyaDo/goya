package com.ysmjjsy.goya.module.domain.event;

import com.ysmjjsy.goya.component.bus.event.queue.EventQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:18
 */
public class DomainEventQueue implements EventQueue {

    private final List<DomainEvent> events = new ArrayList<>();

    public void add(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> queue() {
        return events;
    }
}
