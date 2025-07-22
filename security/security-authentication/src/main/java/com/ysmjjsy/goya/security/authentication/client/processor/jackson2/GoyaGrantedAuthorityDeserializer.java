package com.ysmjjsy.goya.security.authentication.client.processor.jackson2;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysmjjsy.goya.component.common.json.jackson2.utils.JsonNodeUtils;
import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;

import java.io.IOException;

/**
 * <p>Description: GoyaGrantedAuthority 反序列化 </p>
 *
 * @author goya
 * @since 2022/3/17 20:28
 */
public class GoyaGrantedAuthorityDeserializer extends JsonDeserializer<GoyaGrantedAuthority> {
    @Override
    public GoyaGrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        String authority = JsonNodeUtils.findStringValue(jsonNode, "authority");
        return new GoyaGrantedAuthority(authority);
    }
}
