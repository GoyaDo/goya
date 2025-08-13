package com.ysmjjsy.goya.component.common.exception.transaction;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>事务回滚Exception</p>
 *
 * @author goya
 * @since 2025/2/26 21:05
 */
public class TransactionalRollbackException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -6294172943233162176L;

    public TransactionalRollbackException() {
        super();
    }

    public TransactionalRollbackException(String message) {
        super(message);
    }

    public TransactionalRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionalRollbackException(Throwable cause) {
        super(cause);
    }

    protected TransactionalRollbackException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.TRANSACTION_ROLLBACK;
    }
}
