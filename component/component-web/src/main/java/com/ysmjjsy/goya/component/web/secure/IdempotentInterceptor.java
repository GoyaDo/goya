package com.ysmjjsy.goya.component.web.secure;

import com.ysmjjsy.goya.component.web.annotation.Idempotent;
import com.ysmjjsy.goya.component.web.definition.AbstractHandlerInterceptor;
import com.ysmjjsy.goya.component.web.stamp.IdempotentStampManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * <p>Description: 幂等拦截器 </p>
 *
 * @author goya
 * @since 2021/8/22 15:31
 */
public class IdempotentInterceptor extends AbstractHandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(IdempotentInterceptor.class);

    private IdempotentStampManager idempotentStampManager;

    public void setIdempotentStampManager(IdempotentStampManager idempotentStampManager) {
        this.idempotentStampManager = idempotentStampManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();

        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        if (ObjectUtils.isNotEmpty(idempotent)) {
            String key = generateRequestKey(request);
            return IdempotentHandler.handle(key, idempotent, request.getRequestURI(), idempotentStampManager);
        }

        return true;
    }
}
