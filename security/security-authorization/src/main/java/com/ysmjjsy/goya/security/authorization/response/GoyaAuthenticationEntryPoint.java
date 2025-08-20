package com.ysmjjsy.goya.security.authorization.response;

import com.ysmjjsy.goya.component.common.pojo.response.Response;
import com.ysmjjsy.goya.component.web.utils.ResponseUtils;
import com.ysmjjsy.goya.security.core.advice.SecurityGlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * <p>Description: 自定义未认证处理 </p>
 *
 * @author goya
 * @since 2022/3/8 8:55
 */
public class GoyaAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Response<Void> result = SecurityGlobalExceptionHandler.resolveSecurityException(authException, request.getRequestURI());
        response.setStatus(result.getStatus());
        ResponseUtils.renderJson(response, result);
    }
}
