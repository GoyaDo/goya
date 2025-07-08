package com.ysmjjsy.goya.component.exception.request;


import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 非法操作Exception </p>
 *
 * @author goya
 * @since 2021/8/25 17:43
 */
public class IllegalOperationException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -135146527053963147L;

    public IllegalOperationException() {
    }

    public IllegalOperationException(String message) {
        super(message);
    }

    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalOperationException(Throwable cause) {
        super(cause);
    }

    public IllegalOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
