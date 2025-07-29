package com.ysmjjsy.goya.component.exception.captcha;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 验证码分类错误 </p>
 *
 * @author goya
 * @since 2021/12/15 17:51
 */
public class CaptchaCategoryIsIncorrectException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 1227440029914614772L;

    public CaptchaCategoryIsIncorrectException() {
        super();
    }

    public CaptchaCategoryIsIncorrectException(String message) {
        super(message);
    }

    public CaptchaCategoryIsIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaCategoryIsIncorrectException(Throwable cause) {
        super(cause);
    }

    protected CaptchaCategoryIsIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_CATEGORY_IS_INCORRECT;
    }
}
