package com.ysmjjsy.goya.component.common.exception.stamp;

import com.ysmjjsy.goya.component.common.exception.definition.GoyaDefaultException;

import java.io.Serial;

/**
 * <p>Description: Stamp签章 已过期错误 </p>
 *
 * @author goya
 * @since 2021/8/23 12:36
 */
public class StampHasExpiredException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = -4890337069116209100L;

    public StampHasExpiredException() {
        super();
    }

    public StampHasExpiredException(String message) {
        super(message);
    }

    public StampHasExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampHasExpiredException(Throwable cause) {
        super(cause);
    }

    protected StampHasExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
