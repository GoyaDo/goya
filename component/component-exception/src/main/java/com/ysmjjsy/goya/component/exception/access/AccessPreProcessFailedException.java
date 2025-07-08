package com.ysmjjsy.goya.component.exception.access;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 接入预操作失败错误 </p>
 *
 * @author goya
 * @since 2022/1/26 11:10
 */
public class AccessPreProcessFailedException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 5905156522357688253L;

    public AccessPreProcessFailedException() {
    }

    public AccessPreProcessFailedException(String message) {
        super(message);
    }

    public AccessPreProcessFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessPreProcessFailedException(Throwable cause) {
        super(cause);
    }

    public AccessPreProcessFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ACCESS_PRE_PROCESS_FAILED_EXCEPTION;
    }
}
