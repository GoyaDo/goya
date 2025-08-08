package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.StreamMessage;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: Spring Cloud Stream 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2023/10/26 15:17
 */
@Getter
public class StreamMessageSendingEvent<M extends StreamMessage> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -133171772620214673L;

    private final M streamMessage;

    public StreamMessageSendingEvent(Object source, M streamMessage) {
        super(source);
        this.streamMessage = streamMessage;
    }

    @Override
    public EventType eventType() {
        return EventType.MESSAGE_EVENT;
    }
}
