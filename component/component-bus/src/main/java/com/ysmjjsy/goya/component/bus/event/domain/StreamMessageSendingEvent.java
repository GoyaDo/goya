package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.message.domain.StreamMessage;

import java.io.Serial;
import java.time.Clock;

/**
 * <p>Description: Spring Cloud Stream 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2023/10/26 15:17
 */
public class StreamMessageSendingEvent<T extends StreamMessage> extends GoyaAbstractEvent<T> {

    @Serial
    private static final long serialVersionUID = -133171772620214673L;

    public StreamMessageSendingEvent(T data) {
        super(data);
    }

    public StreamMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }
}
