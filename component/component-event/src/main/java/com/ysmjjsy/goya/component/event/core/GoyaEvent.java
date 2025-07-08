package com.ysmjjsy.goya.component.event.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 统一事件接口
 * 扩展Spring ApplicationEvent，支持序列化和远程传输
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class GoyaEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = -1066208137841860861L;

    // Getters
    @JsonProperty("eventId")
    private final String eventId;

    @JsonProperty("eventType")
    private final String eventType;

    @JsonProperty("eventTime")
    private final LocalDateTime eventTime;

    @JsonProperty("source")
    private final String eventSource;

    @Setter
    @JsonProperty("topic")
    private String topic;

    @JsonProperty("headers")
    private final Map<String, Object> headers;

    @Setter
    @JsonProperty("routingStrategy")
    private EventRoutingStrategy routingStrategy = EventRoutingStrategy.getSystemDefault();

    /**
     * 默认构造器 - Jackson反序列化需要
     */
    protected GoyaEvent() {
        // 提供一个虚拟的source
        super(new Object());
        this.eventId = UUID.randomUUID().toString();
        this.eventType = this.getClass().getSimpleName();
        this.eventTime = LocalDateTime.now();
        this.eventSource = "deserialized";
        this.headers = new HashMap<>();
        this.topic = GoyaContextHolder.getInstance().getApplicationName();
    }

    /**
     * JsonCreator构造器 - 支持完整的反序列化
     */
    @JsonCreator
    protected GoyaEvent(@JsonProperty("eventId") String eventId,
                        @JsonProperty("eventType") String eventType,
                        @JsonProperty("eventTime") LocalDateTime eventTime,
                        @JsonProperty("source") String eventSource,
                        @JsonProperty("topic") String topic,
                        @JsonProperty("headers") Map<String, Object> headers,
                        @JsonProperty("routingStrategy") EventRoutingStrategy routingStrategy) {
        // 提供一个虚拟的source，因为ApplicationEvent需要
        super(new Object());
        this.eventId = eventId != null ? eventId : UUID.randomUUID().toString();
        this.eventType = eventType != null ? eventType : this.getClass().getSimpleName();
        this.eventTime = eventTime != null ? eventTime : LocalDateTime.now();
        this.eventSource = eventSource != null ? eventSource : "deserialized";
        this.topic = topic != null ? topic :GoyaContextHolder.getInstance().getApplicationName();
        this.headers = headers != null ? headers : new HashMap<>();
        this.routingStrategy = routingStrategy != null ? routingStrategy : EventRoutingStrategy.getSystemDefault();
    }

    protected GoyaEvent(Object source) {
        super(source);
        this.eventId = UUID.randomUUID().toString();
        this.eventType = this.getClass().getSimpleName();
        this.eventTime = LocalDateTime.now();
        this.eventSource = source.toString();
        this.headers = new HashMap<>();
        this.topic = GoyaContextHolder.getInstance().getApplicationName();
    }

    protected GoyaEvent(Object source, Map<String, Object> headers) {
        this(source);
        if (headers != null) {
            this.headers.putAll(headers);
        }
    }

    // 添加header的便利方法
    public GoyaEvent addHeader(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return String.format("GoyaEvent{eventId='%s', eventType='%s', timestamp=%s, source='%s', routingStrategy=%s}",
                eventId, eventType, eventTime, eventSource, routingStrategy);
    }
}