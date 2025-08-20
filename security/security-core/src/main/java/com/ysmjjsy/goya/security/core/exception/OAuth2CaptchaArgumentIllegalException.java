package com.ysmjjsy.goya.security.core.exception;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

/**
 * <p>Description: Oauth2 使用的验证码参数错误 </p>
 *
 * @author goya
 * @since 2021/12/24 12:02
 */
public class OAuth2CaptchaArgumentIllegalException extends OAuth2CaptchaException {

    public OAuth2CaptchaArgumentIllegalException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaArgumentIllegalException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_PARAMETER_ILLEGAL;
    }
}
