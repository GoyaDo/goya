package com.ysmjjsy.goya.component.bus.message.domain;

import lombok.Data;

import java.io.Serial;

/**
 * <p>Description: Mqtt 类型消息参数实体 </p>
 *
 * @author goya
 * @since 2023/11/2 16:05
 */
@Data
public class MqttMessage implements Message<String> {

    @Serial
    private static final long serialVersionUID = -1801405373829738113L;

    private String topic;
    private String responseTopic;
    private String correlationData;
    private Integer qos;
    private String payload;
}
