package com.ysmjjsy.goya.component.exception.access;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: Access 配置错误 </p>
 *
 * @author goya
 * @since 2022/9/2 18:02
 */
public class AccessConfigErrorException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 3065341998484308080L;

    public AccessConfigErrorException() {
        super();
    }

    public AccessConfigErrorException(String message) {
        super(message);
    }

    public AccessConfigErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessConfigErrorException(Throwable cause) {
        super(cause);
    }

    protected AccessConfigErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ACCESS_CONFIG_ERROR;
    }
}
