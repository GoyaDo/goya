package com.ysmjjsy.goya.security.authentication.client.domain.converter;

import com.ysmjjsy.goya.security.authentication.client.processor.OAuth2JacksonProcessor;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

/**
 * <p>Description: 封装RegisteredClientAdapter 默认行为 </p>
 *
 * @author goya
 * @since 2023/5/12 23:54
 */
public abstract class AbstractOAuth2EntityConverter<S, T> implements Converter<S, T> {

    private final OAuth2JacksonProcessor jacksonProcessor;

    public AbstractOAuth2EntityConverter(OAuth2JacksonProcessor jacksonProcessor) {
        this.jacksonProcessor = jacksonProcessor;
    }

    protected Map<String, Object> parseMap(String data) {
        return jacksonProcessor.parseMap(data);
    }

    protected String writeMap(Map<String, Object> data) {
        return jacksonProcessor.writeMap(data);
    }
}
