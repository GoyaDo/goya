package com.ysmjjsy.goya.component.exception.captcha;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 验证码不匹配错误 </p>
 *
 * @author goya
 * @since 2021/12/15 17:56
 */
public class CaptchaMismatchException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -3977521967996687869L;

    public CaptchaMismatchException() {
        super();
    }

    public CaptchaMismatchException(String message) {
        super(message);
    }

    public CaptchaMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaMismatchException(Throwable cause) {
        super(cause);
    }

    protected CaptchaMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_MISMATCH;
    }
}
