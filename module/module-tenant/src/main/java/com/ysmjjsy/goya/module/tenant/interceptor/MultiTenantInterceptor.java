package com.ysmjjsy.goya.module.tenant.interceptor;

import com.ysmjjsy.goya.component.context.service.TenantContextHolder;
import com.ysmjjsy.goya.component.web.utils.HeaderUtils;
import com.ysmjjsy.goya.component.web.utils.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * <p>Description: 多租户拦截器 </p>
 *
 * @author goya
 * @since 2022/9/6 11:16
 */
public class MultiTenantInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MultiTenantInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String tenantId = HeaderUtils.getGoyaTenantId(request);
        TenantContextHolder.setTenantId(tenantId);
        log.debug("[Goya] |- TENANT ID is : [{}].", tenantId);

        String path = request.getRequestURI();
        String sessionId = SessionUtils.getSessionId(request);
        String goyaSessionId = HeaderUtils.getGoyaSessionId(request);

        log.debug("[Goya] |- SESSION ID for [{}] is : [{}].", path, sessionId);
        log.debug("[Goya] |- SESSION ID of Goya for [{}] is : [{}].", path, goyaSessionId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String path = request.getRequestURI();
        TenantContextHolder.clear();
        log.debug("[Goya] |- Tenant Interceptor CLEAR tenantId for request [{}].", path);
    }
}
