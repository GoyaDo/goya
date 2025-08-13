package com.ysmjjsy.goya.component.common.exception.pool;

import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

import java.io.Serial;

/**
 * <p>获取从连接池中获取对象错误</p>
 *
 * @author goya
 * @since 2025/6/13 15:53
 */
public class BorrowObjectFromPoolErrorException extends GoyaRuntimeException {
    @Serial
    private static final long serialVersionUID = -3103780970302844175L;

    public BorrowObjectFromPoolErrorException() {
    }

    public BorrowObjectFromPoolErrorException(String message) {
        super(message);
    }

    public BorrowObjectFromPoolErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BorrowObjectFromPoolErrorException(Throwable cause) {
        super(cause);
    }

    public BorrowObjectFromPoolErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.BORROW_OBJECT_FROM_POOL_ERROR_EXCEPTION;
    }
}
