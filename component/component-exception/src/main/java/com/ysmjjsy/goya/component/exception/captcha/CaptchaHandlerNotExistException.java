package com.ysmjjsy.goya.component.exception.captcha;

import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.dto.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * <p>Description: 验证码处理器不存在 </p>
 *
 * @author goya
 * @since 2021/12/15 17:53
 */
public class CaptchaHandlerNotExistException extends GoyaRuntimeException {

    @Serial
    private static final long serialVersionUID = 1725708108115951113L;

    public CaptchaHandlerNotExistException() {
        super();
    }

    public CaptchaHandlerNotExistException(String message) {
        super(message);
    }

    public CaptchaHandlerNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaHandlerNotExistException(Throwable cause) {
        super(cause);
    }

    protected CaptchaHandlerNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_HANDLER_NOT_EXIST;
    }
}
