package com.ysmjjsy.goya.security.core.exception;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.exception.definition.GoyaException;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

/**
 * <p>Description: 平台认证基础Exception </p>
 *
 * @author goya
 * @since 2021/10/16 14:41
 */
public class GoyaAuthenticationException extends AuthenticationException implements GoyaException {

    @Serial
    private static final long serialVersionUID = 2065900496873818216L;

    public GoyaAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public GoyaAuthenticationException(String msg) {
        super(msg);
    }

    @Override
    public ErrorCodeService getErrorCode() {
        return ErrorCodes.INTERNAL_SERVER_ERROR;
    }

    @Override
    public Response<Void> getResponse() {
        return Response.failure(getErrorCode());
    }
}
