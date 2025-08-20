package com.ysmjjsy.goya.component.common.exception.definition;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 15:50
 */
public class GoyaRuntimeException extends GoyaAbstractRuntimeException{
    @Serial
    private static final long serialVersionUID = 5855602497641221626L;

    public GoyaRuntimeException() {
    }

    public GoyaRuntimeException(String message) {
        super(message);
    }

    public GoyaRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoyaRuntimeException(Throwable cause) {
        super(cause);
    }

    public GoyaRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.SERVER_ERROR;
    }
}
