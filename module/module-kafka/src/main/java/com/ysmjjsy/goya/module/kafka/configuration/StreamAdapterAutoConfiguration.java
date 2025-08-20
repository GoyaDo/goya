package com.ysmjjsy.goya.module.kafka.configuration;

import com.ysmjjsy.goya.module.kafka.stream.StreamMessageSendingAdapter;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.stream.function.FunctionConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: Stream 消息发送适配器配置 </p>
 *
 * @author goya
 * @since 2025/7/11 00:02
 */
@AutoConfiguration(after = FunctionConfiguration.class)
@ConditionalOnBean(StreamBridge.class)
public class StreamAdapterAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(StreamAdapterAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Goya] |- Module [Stream Adapter] Auto Configure.");
    }

    @Bean
    public StreamMessageSendingAdapter streamMessageSendingAdapter(StreamBridge streamBridge) {
        StreamMessageSendingAdapter adapter = new StreamMessageSendingAdapter(streamBridge);
        log.trace("[Goya] |- Bean [Stream Message Sending Adapter] Auto Configure.");
        return adapter;
    }
}
