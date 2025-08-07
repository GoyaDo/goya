package com.ysmjjsy.goya.component.bus.event.domain;

import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/10 22:41
 */
@Getter
public abstract class GoyaAbstractEvent extends ApplicationEvent implements GoyaEvent {

    @Serial
    private static final long serialVersionUID = -2363499974541919631L;

    /**
     * 事件ID - 全局唯一标识符
     */
    protected String eventId;

    /**
     * 事件key
     */
    protected String key;

    /**
     * 事件创建时间
     */
    protected final LocalDateTime createdTime = LocalDateTime.now();

    /**
     * 事件状态
     */
    protected EventStatus eventStatus;

    protected GoyaAbstractEvent(Object source) {
        super(source);
    }

    protected GoyaAbstractEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
