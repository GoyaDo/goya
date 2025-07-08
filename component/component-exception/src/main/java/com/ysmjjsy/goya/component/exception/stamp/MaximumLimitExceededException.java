package com.ysmjjsy.goya.component.exception.stamp;

import com.ysmjjsy.goya.component.exception.definition.GoyaDefaultException;

import java.io.Serial;

/**
 * <p>Description: 超出最大数量限制 </p>
 *
 * @author goya
 * @since 2022/7/6 23:03
 */
public class MaximumLimitExceededException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = 4867954978081814885L;

    public MaximumLimitExceededException() {
        super();
    }

    public MaximumLimitExceededException(String message) {
        super(message);
    }

    public MaximumLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaximumLimitExceededException(Throwable cause) {
        super(cause);
    }

    protected MaximumLimitExceededException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
