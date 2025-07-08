package com.ysmjjsy.goya.module.ip2region.exception;

import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: TODO </p>
 *
 * @author goya
 * @since 2023/10/24 13:13
 */
public class SearchIpLocationException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -353947157322314458L;

    public SearchIpLocationException() {
        super();
    }

    public SearchIpLocationException(String message) {
        super(message);
    }

    public SearchIpLocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchIpLocationException(Throwable cause) {
        super(cause);
    }

    protected SearchIpLocationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
