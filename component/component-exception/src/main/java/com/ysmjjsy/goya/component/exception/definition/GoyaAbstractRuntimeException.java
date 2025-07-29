package com.ysmjjsy.goya.component.exception.definition;

import com.ysmjjsy.goya.component.pojo.response.Response;

import java.io.Serial;

/**
 * <p>自定义错误基础类</p>
 *
 * @author goya
 * @since 2025/6/13 15:49
 */
public abstract class GoyaAbstractRuntimeException extends RuntimeException implements GoyaException {
    @Serial
    private static final long serialVersionUID = -9098403291201372975L;

    protected GoyaAbstractRuntimeException() {
    }

    protected GoyaAbstractRuntimeException(String message) {
        super(message);
    }

    protected GoyaAbstractRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected GoyaAbstractRuntimeException(Throwable cause) {
        super(cause);
    }

    protected GoyaAbstractRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Response<Void> getResponse() {
        return Response.failure(getErrorCode());
    }
}
