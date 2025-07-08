package com.ysmjjsy.goya.component.common.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ysmjjsy.goya.component.common.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>Description: Timestamp 转 LocalDateTime 反序列化器 </p>
 *
 * @author goya
 * @since 2023/9/22 16:46
 */
public class TimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        long timestamp = jsonParser.getValueAsLong();
        return DateTimeUtils.toLocalDateTime(timestamp);
    }
}
