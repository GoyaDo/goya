package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.WebSocketMessage;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: WebSocket 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2024/4/14 23:46
 */
@Getter
public class WebSocketMessageSendingEvent<P, M extends WebSocketMessage<P>> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -5041710038842542582L;

    private final M webSocketMessage;

    public WebSocketMessageSendingEvent(Object source,M webSocketMessage) {
        super(source);
        this.webSocketMessage = webSocketMessage;
    }

    @Override
    public EventType eventType() {
        return EventType.MESSAGE_EVENT;
    }
}
