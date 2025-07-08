package com.ysmjjsy.goya.component.exception.request;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: Session 不可用错误 </p>
 *
 * @author goya
 * @since 2021/10/4 16:46
 */
public class SessionInvalidException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 1420287360082166386L;

    public SessionInvalidException() {
        super();
    }

    public SessionInvalidException(String message) {
        super(message);
    }

    public SessionInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionInvalidException(Throwable cause) {
        super(cause);
    }

    public SessionInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.SESSION_INVALID;
    }
}
