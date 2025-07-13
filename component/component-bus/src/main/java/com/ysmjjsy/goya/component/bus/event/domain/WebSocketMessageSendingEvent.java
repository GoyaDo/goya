package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.message.domain.WebSocketMessage;

import java.io.Serial;
import java.time.Clock;

/**
 * <p>Description: WebSocket 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2024/4/14 23:46
 */
public class WebSocketMessageSendingEvent<P, T extends WebSocketMessage<P>> extends GoyaAbstractEvent<T> {

    @Serial
    private static final long serialVersionUID = -5041710038842542582L;

    public WebSocketMessageSendingEvent(T data) {
        super(data);
    }

    public WebSocketMessageSendingEvent(T data, Clock clock) {
        super(data, clock);
    }
}
