package com.ysmjjsy.goya.component.common.exception.request;


import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

import java.io.Serial;

/**
 * <p>Description: 操作频繁Exception </p>
 *
 * @author goya
 * @since 2021/8/25 17:29
 */
public class FrequentRequestsException extends IllegalOperationException {

    @Serial
    private static final long serialVersionUID = 2626880970298934661L;

    public FrequentRequestsException() {
    }

    public FrequentRequestsException(String message) {
        super(message);
    }

    public FrequentRequestsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrequentRequestsException(Throwable cause) {
        super(cause);
    }

    public FrequentRequestsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.FREQUENT_REQUESTS;
    }
}
