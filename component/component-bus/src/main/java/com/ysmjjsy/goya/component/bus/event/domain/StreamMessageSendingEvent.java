package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.StreamMessage;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p>Description: Spring Cloud Stream 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2023/10/26 15:17
 */
public class StreamMessageSendingEvent<T extends StreamMessage> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -133171772620214673L;

    public StreamMessageSendingEvent(T data) {
        super(data);
    }

    public StreamMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }

    @Override
    public EventType eventType() {
        return null;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return null;
    }

    @Override
    public void setEventStatus(EventStatus eventStatus) {

    }
}
