package com.ysmjjsy.goya.component.common.exception.access;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 接入处理器未找到错误 </p>
 *
 * @author goya
 * @since 2022/1/26 12:03
 */
public class AccessHandlerNotFoundException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -2272568200166379895L;

    public AccessHandlerNotFoundException() {
        super();
    }

    public AccessHandlerNotFoundException(String message) {
        super(message);
    }

    public AccessHandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessHandlerNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccessHandlerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ACCESS_HANDLER_NOT_FOUND;
    }
}
