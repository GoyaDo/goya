package com.ysmjjsy.goya.module.domain.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;

import java.io.Serial;

/**
 * <p>基础领域事件类</p>
 *
 * @author goya
 * @since 2025/7/30 11:21
 */
public abstract class DomainEvent<E> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -2211065465395168357L;

    protected DomainEvent(E data) {
        super(data);
    }
}
