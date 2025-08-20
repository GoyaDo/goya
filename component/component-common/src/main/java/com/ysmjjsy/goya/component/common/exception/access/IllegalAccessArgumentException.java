package com.ysmjjsy.goya.component.common.exception.access;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 非法的访问参数错误 </p>
 *
 * @author goya
 * @since 2022/1/26 12:02
 */
public class IllegalAccessArgumentException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 3076429179681105838L;

    public IllegalAccessArgumentException() {
        super();
    }

    public IllegalAccessArgumentException(String message) {
        super(message);
    }

    public IllegalAccessArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAccessArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ILLEGAL_ACCESS_ARGUMENT;
    }
}
