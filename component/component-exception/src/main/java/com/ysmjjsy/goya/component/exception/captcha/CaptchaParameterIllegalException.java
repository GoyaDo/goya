package com.ysmjjsy.goya.component.exception.captcha;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 验证码校验参数错误 </p>
 *
 * @author goya
 * @since 2021/12/15 17:54
 */
public class CaptchaParameterIllegalException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = -5112145869253907402L;

    public CaptchaParameterIllegalException() {
        super();
    }

    public CaptchaParameterIllegalException(String message) {
        super(message);
    }

    public CaptchaParameterIllegalException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaParameterIllegalException(Throwable cause) {
        super(cause);
    }

    protected CaptchaParameterIllegalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_PARAMETER_ILLEGAL;
    }
}
