package com.ysmjjsy.goya.component.bus.event.domain;


import com.ysmjjsy.goya.component.bus.message.domain.MqttMessage;

import java.io.Serial;
import java.time.Clock;

/**
 * <p>Description: Mqtt 类型消息 </p>
 *
 * @author goya
 * @since 2023/11/2 16:05
 */
public class MqttMessageSendingEvent extends GoyaAbstractEvent<MqttMessage> {

    @Serial
    private static final long serialVersionUID = -7875707219877053336L;

    public MqttMessageSendingEvent(MqttMessage data) {
        super(data);
    }

    public MqttMessageSendingEvent(MqttMessage data, Clock clock) {
        super(data, clock);
    }
}
