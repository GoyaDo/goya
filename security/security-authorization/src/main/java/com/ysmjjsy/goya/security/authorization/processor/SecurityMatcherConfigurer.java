package com.ysmjjsy.goya.security.authorization.processor;

import com.ysmjjsy.goya.component.common.utils.ListUtils;
import com.ysmjjsy.goya.security.authorization.constants.SecurityResources;
import com.ysmjjsy.goya.security.authorization.domain.GoyaConfigAttribute;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequest;
import com.ysmjjsy.goya.security.authorization.properties.SecurityAuthorizationProperties;
import com.ysmjjsy.goya.security.authorization.utils.WebPathUtils;
import com.ysmjjsy.goya.security.core.enums.PermissionExpression;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>Description: 安全过滤配置处理器 </p>
 * <p>
 * 对静态资源、开放接口等静态配置进行处理。整合默认配置和配置文件中的配置
 *
 * @author goya
 * @since 2025/7/8 22:52
 */
@Component
public class SecurityMatcherConfigurer {

    private final SecurityAuthorizationProperties authorizationProperties;
    private final ResourceUrlProvider resourceUrlProvider;

    @Getter
    private final RequestMatcher[] staticRequestMatchers;
    @Getter
    private final RequestMatcher[] permitAllRequestMatchers;
    @Getter
    private final RequestMatcher[] hasAuthenticatedRequestMatchers;
    @Getter
    private final LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> permitAllAttributes;

    public SecurityMatcherConfigurer(SecurityAuthorizationProperties authorizationProperties, ResourceUrlProvider resourceUrlProvider) {
        this.authorizationProperties = authorizationProperties;
        this.resourceUrlProvider = resourceUrlProvider;
        List<String> staticResources = ListUtils.merge(authorizationProperties.getMatcher().getStaticResources(), SecurityResources.DEFAULT_IGNORED_STATIC_RESOURCES);
        List<String> permitAllResources = ListUtils.merge(authorizationProperties.getMatcher().getPermitAll(), SecurityResources.DEFAULT_PERMIT_ALL_RESOURCES);
        List<String> hasAuthenticatedResources = ListUtils.merge(authorizationProperties.getMatcher().getHasAuthenticated(), SecurityResources.DEFAULT_HAS_AUTHENTICATED_RESOURCES);
        this.permitAllAttributes = createPermitAllAttributes(permitAllResources);
        this.staticRequestMatchers = WebPathUtils.toRequestMatchers(staticResources);
        this.permitAllRequestMatchers = WebPathUtils.toRequestMatchers(permitAllResources);
        this.hasAuthenticatedRequestMatchers = WebPathUtils.toRequestMatchers(hasAuthenticatedResources);
    }

    /**
     * 获取 SecurityFilterChain 中配置的 RequestMatchers 信息。
     * <p>
     * RequestMatchers 可以理解为“静态配置”，将其与平台后端的“动态配置”有机结合。同时，防止因动态配置导致的静态配置失效的问题。
     * <p>
     * 目前只处理了 permitAll 类型。其它内容根据后续使用情况再行添加。
     *
     * @return RequestMatchers 中配置的权限数据
     */
    private LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> createPermitAllAttributes(List<String> permitAllResources) {
        if (CollectionUtils.isNotEmpty(permitAllResources)) {
            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> result = new LinkedHashMap<>();
            permitAllResources.forEach(item -> {
                result.put(new GoyaRequest(item), List.of(new GoyaConfigAttribute(PermissionExpression.PERMIT_ALL.getValue())));
            });
            return result;
        }
        return new LinkedHashMap<>();
    }

    /**
     * 判断是否为静态资源
     *
     * @param uri 请求 URL
     * @return 是否为静态资源
     */
    public boolean isStaticResources(String uri) {
        String staticUri = resourceUrlProvider.getForLookupPath(uri);
        return StringUtils.isNotBlank(staticUri);
    }

    public boolean isStrictMode() {
        return authorizationProperties.getStrict();
    }

    public boolean isPermitAllRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getPermitAllRequestMatchers(), request);
    }

    public boolean isHasAuthenticatedRequest(HttpServletRequest request) {
        return WebPathUtils.isRequestMatched(getHasAuthenticatedRequestMatchers(), request);
    }
}
