package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.message.domain.MqttMessage;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: Mqtt 类型消息 </p>
 *
 * @author goya
 * @since 2023/11/2 16:05
 */
@Getter
public class MqttMessageSendingEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -7875707219877053336L;

    private final MqttMessage mqttMessage;

    public MqttMessageSendingEvent(Object source,MqttMessage mqttMessage) {
        super(source);
        this.mqttMessage = mqttMessage;
    }

    @Override
    public EventType eventType() {
        return EventType.MESSAGE_EVENT;
    }
}
