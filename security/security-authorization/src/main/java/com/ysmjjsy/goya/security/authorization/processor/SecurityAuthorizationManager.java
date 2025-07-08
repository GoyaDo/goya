package com.ysmjjsy.goya.security.authorization.processor;

import com.ysmjjsy.goya.component.web.utils.HeaderUtils;
import com.ysmjjsy.goya.security.authorization.domain.GoyaConfigAttribute;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequest;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequestMatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <p>Spring Security 6 授权管理器</p>
 * Spring Security 6 授权管理
 * 1. 由原来的 AccessDecisionManager 和 AccessDecisionVoter，变更为使用 {@link AuthorizationManager}
 * 2. 原来的 SecurityMetadataSource 已经不再使用。其实想要自己扩展，基本逻辑还是一致。只不过给使用者更大的扩展度和灵活度。
 * 3. 原来的 <code>FilterSecurityInterceptor</code>，已经不再使用。改为使用 {@link org.springframework.security.web.access.intercept.AuthorizationFilter}
 *
 * @author goya
 * @since 2025/7/8 23:41
 */
@Slf4j
public class SecurityAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    
    private final SecurityMetadataSourceStorage securityMetadataSourceStorage;
    private final SecurityMatcherConfigurer securityMatcherConfigurer;

    public SecurityAuthorizationManager(SecurityMetadataSourceStorage securityMetadataSourceStorage, SecurityMatcherConfigurer securityMatcherConfigurer) {
        this.securityMetadataSourceStorage = securityMetadataSourceStorage;
        this.securityMatcherConfigurer = securityMatcherConfigurer;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        final HttpServletRequest request = object.getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();

        if (securityMatcherConfigurer.isStaticResources(url)) {
            log.trace("[Goya] |- Is static resource : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        if (securityMatcherConfigurer.isPermitAllRequest(request)) {
            log.trace("[Goya] |- Is white list resource : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        String feignInnerFlag = HeaderUtils.getGoyaFromIn(request);
        if (StringUtils.isNotBlank(feignInnerFlag)) {
            log.trace("[Goya] |- Is feign inner invoke : [{}], Passed!", url);
            return new AuthorizationDecision(true);
        }

        if (securityMatcherConfigurer.isHasAuthenticatedRequest(request)) {
            log.trace("[Goya] |- Is has authenticated resource : [{}]", url);
            return new AuthorizationDecision(authentication.get().isAuthenticated());
        }

        List<GoyaConfigAttribute> configAttributes = findConfigAttribute(url, method, request);
        if (CollectionUtils.isEmpty(configAttributes)) {
            log.warn("[Goya] |- NO PRIVILEGES : [{}].", url);

            if (!securityMatcherConfigurer.isStrictMode() && authentication.get().isAuthenticated()) {
                    log.debug("[Goya] |- Request is authenticated: [{}].", url);
                    return new AuthorizationDecision(true);
                }


            return new AuthorizationDecision(false);
        }

        for (GoyaConfigAttribute configAttribute : configAttributes) {
            WebExpressionAuthorizationManager webExpressionAuthorizationManager = new WebExpressionAuthorizationManager(configAttribute.getAttribute());
            AuthorizationDecision decision = webExpressionAuthorizationManager.check(authentication, object);
            if (decision.isGranted()) {
                log.debug("[Goya] |- Request [{}] is authorized!", object.getRequest().getRequestURI());
                return decision;
            }
        }

        return new AuthorizationDecision(false);
    }

    private List<GoyaConfigAttribute> findConfigAttribute(String url, String method, HttpServletRequest request) {

        log.debug("[Goya] |- Current Request is : [{}] - [{}]", url, method);

        List<GoyaConfigAttribute> configAttributes = this.securityMetadataSourceStorage.getConfigAttribute(url, method);
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            log.debug("[Goya] |- Get configAttributes from local storage for : [{}] - [{}]", url, method);
            return configAttributes;
        } else {
            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> compatible = this.securityMetadataSourceStorage.getCompatible();
            if (MapUtils.isNotEmpty(compatible)) {
                // 支持含有**通配符的路径搜索
                for (Map.Entry<GoyaRequest, List<GoyaConfigAttribute>> entry : compatible.entrySet()) {
                    GoyaRequestMatcher requestMatcher = new GoyaRequestMatcher(entry.getKey());
                    if (requestMatcher.matches(request)) {
                        log.debug("[Goya] |- Request match the wildcard [{}] - [{}]", entry.getKey(), entry.getValue());
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }
}
