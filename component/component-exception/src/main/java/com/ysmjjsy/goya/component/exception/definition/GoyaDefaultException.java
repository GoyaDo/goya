package com.ysmjjsy.goya.component.exception.definition;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 15:52
 */
public class GoyaDefaultException extends Exception{
    @Serial
    private static final long serialVersionUID = 1581461653085445907L;

    public GoyaDefaultException() {
    }

    public GoyaDefaultException(String message) {
        super(message);
    }

    public GoyaDefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoyaDefaultException(Throwable cause) {
        super(cause);
    }

    public GoyaDefaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
