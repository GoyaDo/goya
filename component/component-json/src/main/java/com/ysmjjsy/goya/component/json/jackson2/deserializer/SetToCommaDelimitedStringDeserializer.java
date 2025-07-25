package com.ysmjjsy.goya.component.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serial;
import java.util.Set;

/**
 * <p>Description: Set集合反序列化为逗号分隔字符串 </p>
 *
 * @author goya
 * @since 2023/5/22 13:55
 */
public class SetToCommaDelimitedStringDeserializer extends StdDeserializer<String> {

    @Serial
    private static final long serialVersionUID = -4124027957431129632L;

    protected SetToCommaDelimitedStringDeserializer() {
        super(String.class);
    }

    @Override
    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(Set.class);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Set<String> collection = jsonParser.readValueAs(new TypeReference<Set<String>>() {
        });

        if (CollectionUtils.isNotEmpty(collection)) {
            return StringUtils.collectionToCommaDelimitedString(collection);
        }

        return null;
    }
}
