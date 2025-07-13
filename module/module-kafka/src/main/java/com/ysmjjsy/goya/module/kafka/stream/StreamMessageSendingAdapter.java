package com.ysmjjsy.goya.module.kafka.stream;

import com.ysmjjsy.goya.component.bus.event.domain.StreamMessageSendingEvent;
import com.ysmjjsy.goya.component.bus.message.adapter.MessageSendingAdapter;
import com.ysmjjsy.goya.component.bus.message.domain.StreamMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cloud.stream.function.StreamBridge;

/**
 * <p>Description: Spring Cloud Stream 消息发送适配器 </p>
 *
 * @author goya
 * @since 2025/7/11 00:02
 */
@Slf4j
@RequiredArgsConstructor
public class StreamMessageSendingAdapter implements MessageSendingAdapter<Object, StreamMessage, StreamMessageSendingEvent<StreamMessage>> {

    private final StreamBridge streamBridge;

    @Override
    public void onApplicationEvent(StreamMessageSendingEvent<StreamMessage> event) {
        StreamMessage message = event.getData();

        if (ObjectUtils.isEmpty(message.getBinderName())) {
            if (ObjectUtils.isEmpty(message.getOutputContentType())) {
                streamBridge.send(message.getBindingName(), message.getBinderName(), message.getPayload());
            } else {
                streamBridge.send(message.getBindingName(), message.getBinderName(), message.getPayload(), message.getOutputContentType());
            }
        } else {
            if (ObjectUtils.isEmpty(message.getOutputContentType())) {
                streamBridge.send(message.getBindingName(), message.getPayload());
            } else {
                streamBridge.send(message.getBindingName(), message.getPayload(), message.getOutputContentType());
            }
        }
    }
}

