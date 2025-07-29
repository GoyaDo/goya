package com.ysmjjsy.goya.security.authorization.response;

import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.web.utils.ResponseUtils;
import com.ysmjjsy.goya.security.core.advice.SecurityGlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * <p>Description: 访问拒绝处理器 </p>
 *
 * @author goya
 * @since 2022/3/8 8:52
 */
public class GoyaAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response<Void> result = SecurityGlobalExceptionHandler.resolveException(accessDeniedException, request.getRequestURI());
        response.setStatus(result.getStatus());
        ResponseUtils.renderJson(response, result);
    }
}
