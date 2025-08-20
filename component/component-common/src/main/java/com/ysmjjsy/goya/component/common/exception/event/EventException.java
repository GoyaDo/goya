package com.ysmjjsy.goya.component.common.exception.event;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>事件异常</p>
 *
 * @author goya
 * @since 2025/6/13 17:47
 */
public class EventException extends GoyaRuntimeException {
    @Serial
    private static final long serialVersionUID = 6195667367532656121L;

    public EventException() {
    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(Throwable cause) {
        super(cause);
    }

    public EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.EVENT_INFRASTRUCTURE_ERROR;
    }
}
