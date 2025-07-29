package com.ysmjjsy.goya.component.exception.openapi;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: API 调用失败<p>
 *
 * @author goya
 * @since 2024/2/27 19:03
 */
public class OpenApiInvokingFailedException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 2660462675909332497L;

    public OpenApiInvokingFailedException() {
        super();
    }

    public OpenApiInvokingFailedException(String message) {
        super(message);
    }

    public OpenApiInvokingFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpenApiInvokingFailedException(Throwable cause) {
        super(cause);
    }

    protected OpenApiInvokingFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.OPENAPI_INVOKING_FAILED;
    }
}
