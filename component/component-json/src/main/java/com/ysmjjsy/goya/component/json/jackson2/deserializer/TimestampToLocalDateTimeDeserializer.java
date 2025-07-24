package com.ysmjjsy.goya.component.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.dromara.hutool.core.math.NumberUtil;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * <p>Description: Timestamp 转 LocalDateTime 反序列化器 </p>
 *
 * @author goya
 * @since 2023/9/22 16:46
 */
public class TimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        long timestamp = jsonParser.getValueAsLong();
        if (!NumberUtil.isZero(timestamp)) {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return null;
    }
}
