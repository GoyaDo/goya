package com.ysmjjsy.goya.component.common.exception.stamp;

import com.ysmjjsy.goya.component.common.exception.definition.GoyaDefaultException;

import java.io.Serial;

/**
 * <p>Description: Stamp签章校验错误 </p>
 *
 * @author goya
 * @since 2021/8/23 12:32
 */
public class StampMismatchException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = 5822295676681467701L;

    public StampMismatchException() {
        super();
    }

    public StampMismatchException(String message) {
        super(message);
    }

    public StampMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampMismatchException(Throwable cause) {
        super(cause);
    }

    protected StampMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
