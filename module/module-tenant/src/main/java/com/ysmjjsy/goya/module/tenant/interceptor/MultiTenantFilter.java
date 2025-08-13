package com.ysmjjsy.goya.module.tenant.interceptor;

import com.ysmjjsy.goya.component.core.context.TenantContextHolder;
import com.ysmjjsy.goya.component.web.utils.HeaderUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * <p>Description: 多租户过滤器 </p>
 *
 * @author goya
 * @since 2022/9/11 15:03
 */
public class MultiTenantFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String tenantId = HeaderUtils.getGoyaTenantId(request);
        TenantContextHolder.setTenantId(tenantId);

        filterChain.doFilter(servletRequest, servletResponse);
        TenantContextHolder.clear();
    }

    @Override
    public void destroy() {
        TenantContextHolder.clear();
    }
}
