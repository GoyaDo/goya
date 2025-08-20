package com.ysmjjsy.goya.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>Description: 无法解析SocialType错误 </p>
 *
 * @author goya
 * @since 2021/5/16 9:37
 */
public class SocialCredentialsParameterBindingFailedException extends AuthenticationException {

    public SocialCredentialsParameterBindingFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SocialCredentialsParameterBindingFailedException(String msg) {
        super(msg);
    }
}
