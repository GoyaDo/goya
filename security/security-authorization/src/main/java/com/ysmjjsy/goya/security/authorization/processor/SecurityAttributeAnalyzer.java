package com.ysmjjsy.goya.security.authorization.processor;

import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;
import com.ysmjjsy.goya.security.authorization.domain.AttributeTransmitter;
import com.ysmjjsy.goya.security.authorization.domain.GoyaConfigAttribute;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequest;
import com.ysmjjsy.goya.security.core.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;

import java.util.*;

/**
 * <p>SecurityMetadata异步处理Service</p>
 *
 * @author goya
 * @since 2025/7/10 23:15
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityAttributeAnalyzer {
    
    private final SecurityMetadataSourceStorage securityMetadataSourceStorage;
    private final SecurityMatcherConfigurer securityMatcherConfigurer;

    /**
     * 直接使用 {@link org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer} 中的方法
     *
     * @param authority 权限
     * @return 权限表达式
     */
    private String hasAuthority(String authority) {
        return "hasAuthority('" + authority + "')";
    }

    /**
     * 将解析后的数据添加对应的分组中
     *
     * @param container 分组结果数据容器
     * @param category  分组类别
     * @param resources 权限数据
     */
    private void appendToGroup(Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> container, Category category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> resources) {
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> value = new LinkedHashMap<>();

        if (container.containsKey(category)) {
            value = container.get(category);
        }

        value.putAll(resources);

        container.put(category, value);
    }

    /**
     * 将各个服务配置的静态权限数据分组
     *
     * @param securityMatchers 静态权限数据
     * @return 分组后的权限数据
     */
    private Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> groupSecurityMatchers(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> securityMatchers) {

        Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> group = new LinkedHashMap<>();

        securityMatchers.forEach((key, value) -> {
            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> resources = new LinkedHashMap<>();
            resources.put(key, value);
            appendToGroup(group, Category.getCategory(key.getPattern()), resources);
        });

        log.debug("[Goya] |- Grouping security matcher by category.");
        return group;
    }

    /**
     * 解析并动态组装所需要的权限。
     * <p>
     * 1. 原 spring-security-oauth
     * Spring Security 基础权限规则，来源于org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl
     * OAuth2 权限规则来源于 org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods
     * 2. 新 spring-authorization-server
     * Spring Security 基础权限规则，来源于{@link org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer}
     * OAuth2 权限规则来源于 目前还没有
     * <p>
     * 具体解析采用的是 Security 的 {@link org.springframework.security.access.AccessDecisionVoter} 方式，而不是采用自定义的 {@link org.springframework.security.access.AccessDecisionManager} 该方式会与默认的 httpsecurity 配置覆盖。
     * · 基本的权限验证采用的是：{@link org.springframework.security.access.vote.RoleVoter}
     * · scope权限采用两种方式：
     * 一种是：Spring Security org.springframework.security.oauth2.provider.vote.ScopeVoter 目前已取消
     * 另一种是：OAuth2 'hasScope'和'hasAnyScope'方式  org.springframework.security.oauth2.provider.expression.OAuth2SecurityExpressionMethods#hasAnyScope(String...)
     * <p>
     * 如果实际应用不满足可以，自己扩展AccessDecisionVoter或者AccessDecisionManager
     *
     * @param securityAttribute {@link AttributeTransmitter}
     * @return security权限定义集合
     */
    private List<GoyaConfigAttribute> analysis(AttributeTransmitter securityAttribute) {

        List<GoyaConfigAttribute> attributes = new ArrayList<>();

        if (StringUtils.isNotBlank(securityAttribute.getPermissions())) {
            String[] permissions = org.springframework.util.StringUtils.commaDelimitedListToStringArray(securityAttribute.getPermissions());
            Arrays.stream(permissions).forEach(item -> attributes.add(new GoyaConfigAttribute(hasAuthority(item))));
        }

        if (StringUtils.isNotBlank(securityAttribute.getWebExpression())) {
            attributes.add(new GoyaConfigAttribute(securityAttribute.getWebExpression()));
        }

        return attributes;
    }

    /**
     * 创建请求和权限的映射数据
     *
     * @param url              请求url
     * @param methods          请求method
     * @param configAttributes Security权限{@link ConfigAttribute}
     * @return 保存请求和权限的映射的Map
     */
    private LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> convert(String url, String methods, List<GoyaConfigAttribute> configAttributes) {
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> result = new LinkedHashMap<>();
        if (StringUtils.isBlank(methods)) {
            result.put(new GoyaRequest(url), configAttributes);
        } else {
            // 如果methods是以逗号分隔的字符串，那么进行拆分处理
            if (StringUtils.contains(methods, SymbolConstants.COMMA)) {
                String[] multiMethod = StringUtils.split(methods, SymbolConstants.COMMA);
                for (String method : multiMethod) {
                    result.put(new GoyaRequest(url, method), configAttributes);
                }
            } else {
                result.put(new GoyaRequest(url, methods), configAttributes);
            }
        }

        return result;
    }

    /**
     * 将 UPMS 分发的 SecurityAttributes 数据进行权限转换并分组
     *
     * @param attributeTransmitter 权限数据
     * @return 分组后的权限数据
     */
    private Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> groupingSecurityMetadata(List<AttributeTransmitter> attributeTransmitter) {

        Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> group = new LinkedHashMap<>();

        attributeTransmitter.forEach(securityAttribute -> {
            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> resources = convert(securityAttribute.getUrl(), securityAttribute.getRequestMethod(), analysis(securityAttribute));
            appendToGroup(group, Category.getCategory(securityAttribute.getUrl()), resources);
        });

        log.debug("[Goya] |- Grouping security metadata by category.");
        return group;
    }

    /**
     * 各个服务静态化配置的权限过滤，通常为通配符型或者全路径型，很少有站位符型。即：大多数情况为 {@code Category.WILDCARD} 和 {@code Category.PLACEHOLDER}，很少有 {@code Category.FULL_PATH}
     * <p>
     * 此处的逻辑是：
     * 1. 先处理各个服务静态化配置的权限，当前假设不会有{@code Category.FULL_PATH}类型的权限。后期如果该种权限较多再补充即可。
     * 同时，静态服务都是开发人员手工配置，假定手工配置时就会对是否冲突进行处理，当然也可能出现冲突，那么这个开发人员得多不负责。
     * 2. 经过考虑，服务本地接口扫描完，就对所有的 RequestMapping 做一遍解析，现在感觉意义不大。
     * 因为，RequestMapping 汇总至 UPMS 后，还会做一次统一的分发。所以当前的设计思路是不对 RequestMapping 进行处理。后续根据需要再补充即可。
     */
    public void processRequestMatchers() {

        log.debug("[Goya] |- [3] Process local configured security metadata.");

        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> requestMatchers = securityMatcherConfigurer.getPermitAllAttributes();
        if (MapUtils.isNotEmpty(requestMatchers)) {
            Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> grouping = groupSecurityMatchers(requestMatchers);

            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> wildcards = grouping.get(Category.WILDCARD);
            securityMetadataSourceStorage.addToStorage(wildcards, false);

            LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> fullPaths = grouping.get(Category.FULL_PATH);
            securityMetadataSourceStorage.addToStorage(fullPaths, true);
        }
    }

    /**
     * 处理分发的 AttributeTransmitter，将其转换、解析为表达式权限，并存入本地缓存，用于权限校验
     * <p>
     * 处理过程中，会根据规则对权限类型分组，然后进行去重的操作。
     *
     * @param attributeTransmitter 权限数据
     */
    public void processSecurityAttribute(List<AttributeTransmitter> attributeTransmitter) {

        // 从缓存中获取全部带有特殊字符的匹配规则
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> compatibles = securityMetadataSourceStorage.getCompatible();
        // 创建一个临时的 Matcher 容器
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> matchers = new LinkedHashMap<>(compatibles);

        // 对分发的 AttributeTransmitter 进行分组
        Map<Category, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> grouping = groupingSecurityMetadata(attributeTransmitter);

        // 拿到带有通配符的分组数据
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> wildcards = grouping.get(Category.WILDCARD);
        if (MapUtils.isNotEmpty(wildcards)) {
            matchers.putAll(wildcards);
            securityMetadataSourceStorage.addToStorage(wildcards, false);
        }

        // 拿到带有占位符的分组数据，并检测是否存在冲突的匹配规则，然后将结果存入本地存储
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> placeholders = grouping.get(Category.PLACEHOLDER);
        log.debug("[Goya] |- Store placeholder type security attributes.");
        securityMetadataSourceStorage.addToStorage(matchers, placeholders, false);

        // 拿到全路径的分组数据，并检测是否存在冲突的匹配规则，然后将结果存入本地存储
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> fullPaths = grouping.get(Category.FULL_PATH);
        log.debug("[Goya] |- Store full path type security attributes.");
        securityMetadataSourceStorage.addToStorage(matchers, fullPaths, true);

        log.debug("[Goya] |- [7] Security attributes process has FINISHED!");
    }
}
