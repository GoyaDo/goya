package com.ysmjjsy.goya.component.event.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 Jackson 的事件序列化器
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Getter
public class JacksonEventSerializer implements EventSerializer {

    private static final Logger logger = LoggerFactory.getLogger(JacksonEventSerializer.class);
    
    private final ObjectMapper objectMapper;

    public JacksonEventSerializer() {
        this.objectMapper = createObjectMapper();
    }

    public JacksonEventSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        // 启用类型信息以支持多态序列化
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper;
    }

    @Override
    public String serialize(GoyaEvent event) throws SerializationException {
        try {
            String json = objectMapper.writeValueAsString(event);
            logger.debug("Serialized event {}: {}", event.getEventId(), json);
            return json;
        } catch (JsonProcessingException e) {
            throw new SerializationException("Failed to serialize event: " + event.getEventId(), e);
        }
    }

    @Override
    public <T extends GoyaEvent> T deserialize(String data, Class<T> eventType) throws SerializationException {
        try {
            T event = objectMapper.readValue(data, eventType);
            logger.debug("Deserialized event {}: {}", event.getEventId(), event.getEventType());
            return event;
        } catch (JsonProcessingException e) {
            throw new SerializationException("Failed to deserialize event data: " + data, e);
        }
    }

    @Override
    public boolean supports(Class<? extends GoyaEvent> eventType) {
        // Jackson 可以序列化所有 UnifiedEvent 子类
        return GoyaEvent.class.isAssignableFrom(eventType);
    }

}