package com.ysmjjsy.goya.security.core.advice;

import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodeService;
import com.ysmjjsy.goya.component.pojo.errorcode.ErrorCodes;
import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.exception.handler.GlobalExceptionHandler;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:33
 */
@Slf4j
public class SecurityGlobalExceptionHandler {

    private static final Map<String, ErrorCodeService> EXCEPTION_DICTIONARY = new HashMap<>();

    static {
        EXCEPTION_DICTIONARY.put(SecurityConstants.ACCESS_DENIED, ErrorCodes.ACCESS_DENIED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INSUFFICIENT_SCOPE, ErrorCodes.INSUFFICIENT_SCOPE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_CLIENT, ErrorCodes.INVALID_CLIENT);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_GRANT, ErrorCodes.INVALID_GRANT);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_REDIRECT_URI, ErrorCodes.INVALID_REDIRECT_URI);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_REQUEST, ErrorCodes.INVALID_REQUEST);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_SCOPE, ErrorCodes.INVALID_SCOPE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.INVALID_TOKEN, ErrorCodes.INVALID_TOKEN);
        EXCEPTION_DICTIONARY.put(SecurityConstants.SERVER_ERROR, ErrorCodes.SERVER_ERROR);
        EXCEPTION_DICTIONARY.put(SecurityConstants.TEMPORARILY_UNAVAILABLE, ErrorCodes.TEMPORARILY_UNAVAILABLE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.UNAUTHORIZED_CLIENT, ErrorCodes.UNAUTHORIZED_CLIENT);
        EXCEPTION_DICTIONARY.put(SecurityConstants.UNSUPPORTED_GRANT_TYPE, ErrorCodes.UNSUPPORTED_GRANT_TYPE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.UNSUPPORTED_RESPONSE_TYPE, ErrorCodes.UNSUPPORTED_RESPONSE_TYPE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.UNSUPPORTED_TOKEN_TYPE, ErrorCodes.UNSUPPORTED_TOKEN_TYPE);
        EXCEPTION_DICTIONARY.put(SecurityConstants.ACCOUNT_EXPIRED, ErrorCodes.ACCOUNT_EXPIRED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.BAD_CREDENTIALS, ErrorCodes.BAD_CREDENTIALS);
        EXCEPTION_DICTIONARY.put(SecurityConstants.CREDENTIALS_EXPIRED, ErrorCodes.CREDENTIALS_EXPIRED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.ACCOUNT_DISABLED, ErrorCodes.ACCOUNT_DISABLED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.ACCOUNT_LOCKED, ErrorCodes.ACCOUNT_LOCKED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.ACCOUNT_ENDPOINT_LIMITED, ErrorCodes.ACCOUNT_ENDPOINT_LIMITED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.USERNAME_NOT_FOUND, ErrorCodes.USERNAME_NOT_FOUND);
        EXCEPTION_DICTIONARY.put(SecurityConstants.SESSION_EXPIRED, ErrorCodes.SESSION_EXPIRED);
        EXCEPTION_DICTIONARY.put(SecurityConstants.NOT_AUTHENTICATED, ErrorCodes.NOT_AUTHENTICATED);
    }

    public static Response<Void> resolveException(Exception ex, String path) {
        return GlobalExceptionHandler.resolveException(ex, path);
    }

    private static Response<Void> handle(Exception exception, String path, String key, BiFunction<ErrorCodeService, String, Response<Void>> toResult) {
        Optional<ErrorCodeService> optional = Optional.ofNullable(EXCEPTION_DICTIONARY.get(key));
        return optional
                .map(feedback -> toResult.apply(feedback, key))
                .orElseGet(() -> resolveException(exception, path));
    }

    /**
     * 静态解析认证异常
     *
     * @param exception 错误信息
     * @return Result 对象
     */
    public static Response<Void> resolveSecurityException(Exception exception, String path) {
        switch (exception) {
            case OAuth2AuthenticationException oauth2AuthenticationException -> {
                OAuth2Error oauth2Error = oauth2AuthenticationException.getError();
                String key = oauth2Error.getErrorCode();
                return handle(exception, path, key, (feedback, data) -> {
                    Response<Void> result = Response.failure(feedback, data);
                    result.path(oauth2Error.getUri());
                    return result;
                });
            }
            case AuthenticationCredentialsNotFoundException authenticationCredentialsNotFoundException -> {
                String detailMessage = exception.getMessage();
                return handle(authenticationCredentialsNotFoundException, path, detailMessage, Response::failure);
            }
            case InsufficientAuthenticationException insufficientAuthenticationException -> {
                Exception reason = insufficientAuthenticationException;
                Throwable throwable = exception.getCause();
                if (ObjectUtils.isNotEmpty(throwable)) {
                    reason = new Exception(throwable);
                    log.debug("[Goya] |- InsufficientAuthenticationException cause content is [{}]", reason.getClass().getSimpleName());
                }
                return resolveException(reason, path);
            }
            default -> {
                String exceptionName = exception.getClass().getSimpleName();
                return handle(exception, path, exceptionName, Response::failure);
            }
        }
    }
}
