package com.ysmjjsy.goya.component.bus.event.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Clock;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/10 22:41
 */
@Getter
public abstract class GoyaAbstractEvent<T> extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -2363499974541919631L;
    
    private final T data;

    protected GoyaAbstractEvent(T data) {
        super(data);
        this.data = data;
    }

    protected GoyaAbstractEvent(T data, Clock clock) {
        super(data, clock);
        this.data = data;
    }

}
