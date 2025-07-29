package com.ysmjjsy.goya.security.core.exception;


import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;

import java.io.Serial;

/**
 * <p> Description : 非法加密Key GoyaException </p>
 *
 * @author goya
 * @since 2020/1/28 17:32
 */
public class IllegalSymmetricKeyException extends GoyaAuthenticationException {

    @Serial
    private static final long serialVersionUID = -3753393231283032696L;

    public IllegalSymmetricKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalSymmetricKeyException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.ILLEGAL_SYMMETRIC_KEY;
    }
}
