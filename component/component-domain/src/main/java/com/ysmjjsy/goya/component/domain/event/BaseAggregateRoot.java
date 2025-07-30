package com.ysmjjsy.goya.component.domain.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 11:27
 */
public abstract class BaseAggregateRoot {

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

    public BaseAggregateRoot andEvent(DomainEvent event) {
        this.registerEvent(event);
        return this;
    }

    public BaseAggregateRoot andEventsFrom(BaseAggregateRoot aggregate) {
        if (aggregate != null) {
            this.domainEvents.addAll(aggregate.getDomainEvents());
        }
        return this;
    }
}
