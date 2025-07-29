package com.ysmjjsy.goya.component.exception.url;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: Url 格式错误 </p>
 *
 * @author goya
 * @since 2022/3/6 12:49GoyaRuntimeException
 */
public class UrlFormatIncorrectException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 8162837116346762240L;

    public UrlFormatIncorrectException() {
        super();
    }

    public UrlFormatIncorrectException(String message) {
        super(message);
    }

    public UrlFormatIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlFormatIncorrectException(Throwable cause) {
        super(cause);
    }

    protected UrlFormatIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.URL_FORMAT_INCORRECT_EXCEPTION;
    }
}
