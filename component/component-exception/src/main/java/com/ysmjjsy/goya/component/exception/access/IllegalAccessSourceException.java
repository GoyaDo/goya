package com.ysmjjsy.goya.component.exception.access;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 非法的外部访问参数类型错误 </p>
 *
 * @author goya
 * @since 2022/1/26 12:02
 */
public class IllegalAccessSourceException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -2512038796147030742L;

    public IllegalAccessSourceException() {
        super();
    }

    public IllegalAccessSourceException(String message) {
        super(message);
    }

    public IllegalAccessSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessSourceException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessSourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ILLEGAL_ACCESS_SOURCE;
    }
}
