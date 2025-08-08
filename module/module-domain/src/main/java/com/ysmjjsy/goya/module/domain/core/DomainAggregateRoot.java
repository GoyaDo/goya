package com.ysmjjsy.goya.module.domain.core;

import com.ysmjjsy.goya.module.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 11:27
 */
@Getter
@Setter
public abstract class DomainAggregateRoot {

    private final transient List<DomainEvent> domainEvents = new ArrayList<>();

    protected <T extends DomainEvent> T registerEvent(T event) {
        if (event != null) {
            domainEvents.add(event);
        }
        return event;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public DomainAggregateRoot andEvent(DomainEvent event) {
        this.registerEvent(event);
        return this;
    }

    public DomainAggregateRoot andEventsFrom(DomainAggregateRoot aggregate) {
        if (aggregate != null) {
            this.domainEvents.addAll(aggregate.getDomainEvents());
        }
        return this;
    }
}
