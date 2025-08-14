package com.ysmjjsy.goya.component.secure.interceptor;

import com.ysmjjsy.goya.component.secure.annotation.AccessLimited;
import com.ysmjjsy.goya.component.secure.handler.AccessLimitedHandler;
import com.ysmjjsy.goya.component.web.definition.AbstractHandlerInterceptor;
import com.ysmjjsy.goya.component.secure.stamp.AccessLimitedStampManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * <p>Description: 访问防刷拦截器 </p>
 *
 * @author goya
 * @since 2021/8/25 22:09
 */
@Setter
public class AccessLimitedInterceptor extends AbstractHandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedInterceptor.class);

    private AccessLimitedStampManager accessLimitedStampManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.trace("[Goya] |- AccessLimitedInterceptor preHandle postProcess.");

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();

        AccessLimited accessLimited = method.getAnnotation(AccessLimited.class);
        if (ObjectUtils.isNotEmpty(accessLimited)) {
            String key = generateRequestKey(request);
            return AccessLimitedHandler.handle(key, accessLimited, request.getRequestURI(), accessLimitedStampManager);
        }

        return true;
    }
}
