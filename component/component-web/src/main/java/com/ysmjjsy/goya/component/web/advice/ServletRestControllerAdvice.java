package com.ysmjjsy.goya.component.web.advice;

import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.exception.definition.GoyaDefaultException;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;
import com.ysmjjsy.goya.component.exception.handler.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/15 00:42
 */
@RestControllerAdvice
public class ServletRestControllerAdvice {

    public static Response<Void> resolveException(Exception ex, String path) {
        return GlobalExceptionHandler.resolveException(ex, path);
    }

    private static Response<Void> resolveException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        Response<Void> result = resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * Rest Template 错误处理
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return R 对象
     * @see <a href="https://www.baeldung.com/spring-rest-template-error-handling">baeldung</a>
     */
    @ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class})
    public static Response<Void> restTemplateException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return resolveException(ex, request, response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public static Response<Void> validationMethodArgumentException(MethodArgumentNotValidException ex, HttpServletRequest request, HttpServletResponse response) {
        return validationBindException(ex, request, response);
    }

    @ExceptionHandler({BindException.class})
    public static Response<Void> validationBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        Response<Void> result = GlobalExceptionHandler.resolveException(ex, request.getRequestURI());
        response.setStatus(result.getStatus());
        return result;
    }

    /**
     * 统一异常处理
     * AuthenticationException
     *
     * @param ex       错误
     * @param request  请求
     * @param response 响应
     * @return R 对象
     */

    @ExceptionHandler({Exception.class, GoyaDefaultException.class, GoyaRuntimeException.class})
    public static Response<Void> exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        return resolveException(ex, request, response);
    }
}
