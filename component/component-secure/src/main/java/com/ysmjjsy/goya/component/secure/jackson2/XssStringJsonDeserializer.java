package com.ysmjjsy.goya.component.secure.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.ysmjjsy.goya.component.core.utils.XssUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * <p>Description: Xss Json 处理 </p>
 *
 * @author goya
 * @since 2021/8/30 23:58
 */
@Slf4j
public class XssStringJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public Class<String> handledType() {
        return String.class;
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            return XssUtils.cleaning(value);
        }

        return value;
    }
}
