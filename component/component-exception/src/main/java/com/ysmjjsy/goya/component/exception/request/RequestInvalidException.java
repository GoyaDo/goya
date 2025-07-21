package com.ysmjjsy.goya.component.exception.request;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: Request 不可用错误 </p>
 *
 * @author goya
 * @since 2021/10/4 16:46
 */
public class RequestInvalidException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 1420287360082166386L;

    public RequestInvalidException() {
        super();
    }

    public RequestInvalidException(String message) {
        super(message);
    }

    public RequestInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestInvalidException(Throwable cause) {
        super(cause);
    }

    public RequestInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.SESSION_INVALID;
    }
}
