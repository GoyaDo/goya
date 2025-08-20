package com.ysmjjsy.goya.component.common.exception.stamp;

import com.ysmjjsy.goya.component.common.exception.definition.GoyaDefaultException;

import java.io.Serial;

/**
 * <p>Description: 请求参数中缺少幂等Token错误 </p>
 *
 * @author goya
 * @since 2021/8/23 12:29
 */
public class StampParameterIllegalException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = -8229112391552061904L;

    public StampParameterIllegalException() {
        super();
    }

    public StampParameterIllegalException(String message) {
        super(message);
    }

    public StampParameterIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public StampParameterIllegalException(Throwable cause) {
        super(cause);
    }

    protected StampParameterIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
