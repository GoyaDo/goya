package com.ysmjjsy.goya.security.core.exception;

import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.common.pojo.errorcode.ErrorCodes;

/**
 * <p>Description: Oauth2 使用的验证码不匹配错误 </p>
 *
 * @author goya
 * @since 2021/12/24 12:04
 */
public class OAuth2CaptchaHasExpiredException extends OAuth2CaptchaException {

    public OAuth2CaptchaHasExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public OAuth2CaptchaHasExpiredException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.CAPTCHA_HAS_EXPIRED;
    }
}
