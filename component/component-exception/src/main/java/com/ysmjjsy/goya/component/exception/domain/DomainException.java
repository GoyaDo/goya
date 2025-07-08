package com.ysmjjsy.goya.component.exception.domain;

import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:31
 */
public class DomainException extends GoyaRuntimeException {
    @Serial
    private static final long serialVersionUID = -90350891226026336L;

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(Throwable cause) {
        super(cause);
    }

    public DomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
