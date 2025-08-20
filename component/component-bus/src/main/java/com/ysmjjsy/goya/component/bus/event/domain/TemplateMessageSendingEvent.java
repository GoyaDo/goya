package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.TemplateMessage;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: Spring 框架 MessageTemplate 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2023/10/26 15:16
 */
@Getter
public class TemplateMessageSendingEvent<M extends TemplateMessage> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 2066495762060726290L;

    private final M templateMessage;

    public TemplateMessageSendingEvent(Object source,M templateMessage) {
        super(source);
        this.templateMessage = templateMessage;
    }

    @Override
    public EventType eventType() {
        return EventType.MESSAGE_EVENT;
    }
}
