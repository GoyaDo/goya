package com.ysmjjsy.goya.security.core.exception;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

/**
 * <p>Description: 验证码为空 </p>
 *
 * @author goya
 * @since 2021/12/24 18:08
 */
public class OAuth2CaptchaIsEmptyException extends OAuth2CaptchaException {

    public OAuth2CaptchaIsEmptyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaIsEmptyException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_IS_EMPTY;
    }
}
