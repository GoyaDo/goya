package com.ysmjjsy.goya.component.event.serializer;

import com.ysmjjsy.goya.component.exception.definition.GoyaDefaultException;

import java.io.Serial;

/**
 * 序列化异常
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class SerializationException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = -1061648262069140156L;

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }
}