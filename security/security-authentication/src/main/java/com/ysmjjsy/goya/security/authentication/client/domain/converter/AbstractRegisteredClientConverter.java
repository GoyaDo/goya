package com.ysmjjsy.goya.security.authentication.client.domain.converter;

import com.ysmjjsy.goya.security.authentication.client.domain.definition.RegisteredClientDetails;
import com.ysmjjsy.goya.security.authentication.client.processor.OAuth2JacksonProcessor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <p>Description: RegisteredClient 转换器</p>
 *
 * @author goya
 * @since 2023/5/12 23:07
 */
public abstract class AbstractRegisteredClientConverter<S extends RegisteredClientDetails> extends AbstractOAuth2EntityConverter<S, RegisteredClient> implements RegisteredClientConverter<S> {

    public AbstractRegisteredClientConverter(OAuth2JacksonProcessor jacksonProcessor) {
        super(jacksonProcessor);
    }

    @Override
    public RegisteredClient convert(S details) {
        return RegisteredClientConverter.super.convert(details);
    }
}
