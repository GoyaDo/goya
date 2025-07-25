package com.ysmjjsy.goya.component.json.jackson2.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serial;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>Description: 数组转字符串序列化 </p>
 *
 * @author goya
 * @since 2022/3/18 12:16
 */
public class ArrayOrStringToSetDeserializer extends StdDeserializer<Set<String>> {

    @Serial
    private static final long serialVersionUID = -1384868753909190595L;

    public ArrayOrStringToSetDeserializer() {
        super(Set.class);
    }

    @Override
    public JavaType getValueType() {
        return TypeFactory.defaultInstance().constructType(String.class);
    }

    @Override
    public Set<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken token = jsonParser.getCurrentToken();
        if (token.isScalarValue()) {
            String value = jsonParser.getText();
            value = value.replaceAll("\\s+", ",");
            return new LinkedHashSet<>(Arrays.asList(StringUtils.commaDelimitedListToStringArray(value)));
        } else {
            return jsonParser.readValueAs(new TypeReference<Set<String>>() {
            });
        }
    }


}
