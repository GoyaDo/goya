package com.ysmjjsy.goya.module.redis.event;

import com.ysmjjsy.goya.component.event.core.EventHandleException;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import com.ysmjjsy.goya.component.event.core.GoyaEventListener;
import com.ysmjjsy.goya.component.event.serializer.EventSerializer;
import com.ysmjjsy.goya.component.event.transport.EventTransport;
import com.ysmjjsy.goya.component.event.transport.TransportHealth;
import com.ysmjjsy.goya.component.event.transport.TransportResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis 事件传输实现
 * 使用 Redis Pub/Sub 机制实现事件传输
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class RedisEventTransport implements EventTransport {

    private static final Logger logger = LoggerFactory.getLogger(RedisEventTransport.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final EventSerializer eventSerializer;
    private final RedisMessageListenerContainer messageListenerContainer;
    private final Map<String, Map<GoyaEventListener<?>, MessageListener>> subscriptions;

    private volatile boolean started = false;

    public RedisEventTransport(RedisTemplate<String, String> redisTemplate,
                               EventSerializer eventSerializer,
                               RedisMessageListenerContainer messageListenerContainer) {
        this.redisTemplate = redisTemplate;
        this.eventSerializer = eventSerializer;
        this.messageListenerContainer = messageListenerContainer;
        this.subscriptions = new ConcurrentHashMap<>();
    }

    @Override
    public String getTransportType() {
        return "redis";
    }

    @Override
    public CompletableFuture<TransportResult> send(GoyaEvent event, String topic) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String serializedEvent = eventSerializer.serialize(event);
                redisTemplate.convertAndSend(topic, serializedEvent);

                logger.debug("Event sent to Redis topic '{}': {}", topic, event.getEventId());
                return TransportResult.success(getTransportType(), topic, event.getEventId());

            } catch (Exception e) {
                logger.error("Failed to send event {} to Redis topic '{}'", event.getEventId(), topic, e);
                return TransportResult.failure(getTransportType(), topic, event.getEventId(), e);
            }
        });
    }

    @Override
    public <T extends GoyaEvent> void subscribe(String topic, GoyaEventListener<T> listener, Class<T> eventType) {
        if (!started) {
            throw new IllegalStateException("Redis transport not started");
        }

        // 修复：直接实现MessageListener接口，避免MessageListenerAdapter的invoker问题
        MessageListener messageListener = new RedisEventMessageListener<>(listener, eventType, eventSerializer, topic);

        subscriptions.computeIfAbsent(topic, k -> new ConcurrentHashMap<>())
                .put(listener, messageListener);

        messageListenerContainer.addMessageListener(messageListener, new ChannelTopic(topic));

        logger.info("Subscribed to Redis topic '{}' for event type {}", topic, eventType.getSimpleName());
    }

    @Override
    public void unsubscribe(String topic, GoyaEventListener<?> listener) {
        Map<GoyaEventListener<?>, MessageListener> topicSubscriptions = subscriptions.get(topic);
        if (topicSubscriptions != null) {
            MessageListener messageListener = topicSubscriptions.remove(listener);
            if (messageListener != null) {
                messageListenerContainer.removeMessageListener(messageListener, new ChannelTopic(topic));
                logger.info("Unsubscribed from Redis topic '{}'", topic);
            }
        }
    }

    @Override
    public void start() {
        if (!started) {
            messageListenerContainer.start();
            started = true;
            logger.info("Redis event transport started");
        }
    }

    @Override
    public void stop() {
        if (started) {
            messageListenerContainer.stop();
            subscriptions.clear();
            started = false;
            logger.info("Redis event transport stopped");
        }
    }

    @Override
    public TransportHealth getHealth() {
        try {
            // 简单的健康检查：执行一个Redis命令
            redisTemplate.hasKey("health_check");
            return TransportHealth.up(getTransportType());
        } catch (Exception e) {
            return TransportHealth.down(getTransportType(), "Redis connection failed: " + e.getMessage());
        }
    }

    @Override
    public boolean supportsTransaction() {
        // Redis Pub/Sub 不支持事务
        return false;
    }

    @Override
    public boolean supportsOrdering() {
        // Redis Pub/Sub 不保证消息顺序
        return false;
    }

    /**
     * 修复：Redis 消息监听器实现
     * 直接实现MessageListener接口，避免MessageListenerAdapter的复杂性
     */
    private static class RedisEventMessageListener<T extends GoyaEvent> implements MessageListener {

        private static final Logger logger = LoggerFactory.getLogger(RedisEventMessageListener.class);

        private final GoyaEventListener<T> listener;
        private final Class<T> eventType;
        private final EventSerializer eventSerializer;
        private final String topic;

        public RedisEventMessageListener(GoyaEventListener<T> listener, Class<T> eventType,
                                         EventSerializer eventSerializer, String topic) {
            this.listener = listener;
            this.eventType = eventType;
            this.eventSerializer = eventSerializer;
            this.topic = topic;
        }

        @Override
        public void onMessage(Message message, byte[] pattern) {
            try {
                // 将字节数组转换为字符串
                String messageBody = new String(message.getBody());

                logger.debug("Received Redis message on topic '{}': {}", topic, messageBody);

                // 反序列化事件
                T event = eventSerializer.deserialize(messageBody, eventType);

                // 调用监听器处理事件
                listener.onEvent(event);

                logger.debug("Successfully processed Redis event {} on topic '{}'",
                        event.getEventId(), topic);

            } catch (EventHandleException e) {
                logger.error("Failed to handle Redis event on topic '{}': {}", topic, e.getMessage(), e);
            } catch (Exception e) {
                logger.error("Failed to process Redis message on topic '{}': {}", topic, e.getMessage(), e);
            }
        }

        @Override
        public String toString() {
            return String.format("RedisEventMessageListener{topic='%s', eventType=%s, listener=%s}",
                    topic, eventType.getSimpleName(), listener.getClass().getSimpleName());
        }
    }
}