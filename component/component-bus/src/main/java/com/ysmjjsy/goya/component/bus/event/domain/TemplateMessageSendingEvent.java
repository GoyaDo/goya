package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.TemplateMessage;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p>Description: Spring 框架 MessageTemplate 类型消息发送事件 </p>
 *
 * @author goya
 * @since 2023/10/26 15:16
 */
public class TemplateMessageSendingEvent<T extends TemplateMessage> extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 2066495762060726290L;

    public TemplateMessageSendingEvent(T data) {
        super(data);
    }

    public TemplateMessageSendingEvent(T data, Clock clock) {
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
