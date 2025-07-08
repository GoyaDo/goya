package com.ysmjjsy.goya.security.authorization.processor;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.ysmjjsy.goya.component.cache.utils.JetCacheUtils;
import com.ysmjjsy.goya.security.authorization.domain.GoyaConfigAttribute;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequest;
import com.ysmjjsy.goya.security.authorization.domain.GoyaRequestMatcher;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <p>Description: SecurityAttribute 本地存储 </p>
 *
 * @author goya
 * @since 2025/7/8 23:41
 */
public class SecurityMetadataSourceStorage {

    private static final Logger log = LoggerFactory.getLogger(SecurityMetadataSourceStorage.class);
    private static final String KEY_COMPATIBLE = "COMPATIBLE";
    /**
     * 模式匹配权限缓存。主要存储 包含 "*"、"?" 和 "{"、"}" 等特殊字符的路径权限。
     * 该种权限，需要通过遍历，利用 AntPathRequestMatcher 机制进行匹配
     */
    private final Cache<String, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>>> compatible;
    /**
     * 直接索引权限缓存，主要存储全路径权限
     * 该种权限，直接通过 Map Key 进行获取
     */
    private final Cache<GoyaRequest, List<GoyaConfigAttribute>> indexable;

    public SecurityMetadataSourceStorage() {
        this.compatible = JetCacheUtils.create(SecurityConstants.CACHE_NAME_SECURITY_METADATA_COMPATIBLE, CacheType.BOTH, null, true, true);
        this.indexable = JetCacheUtils.create(SecurityConstants.CACHE_NAME_SECURITY_METADATA_INDEXABLE, CacheType.BOTH, null, true, true);
    }

    /**
     * 从 compatible 缓存中读取数据。
     *
     * @return 需要进行模式匹配的权限数据
     */
    private LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> readFromCompatible() {
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> compatible = this.compatible.get(KEY_COMPATIBLE);
        if (MapUtils.isNotEmpty(compatible)) {
            return compatible;
        }
        return new LinkedHashMap<>();

    }

    /**
     * 写入 compatible 缓存
     *
     * @param compatible 请求路径和权限配置属性映射Map
     */
    private void writeToCompatible(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> compatible) {
        this.compatible.put(KEY_COMPATIBLE, compatible);
    }

    /**
     * 从 indexable 缓存中读取数据
     *
     * @param goyaRequest 自定义扩展的 AntPathRequestMatchers {@link GoyaRequest}
     * @return 权限配置属性对象集合
     */
    private List<GoyaConfigAttribute> readFromIndexable(GoyaRequest goyaRequest) {
        return this.indexable.get(goyaRequest);
    }

    /**
     * 写入 indexable 缓存
     *
     * @param goyaRequest 自定义扩展的 AntPathRequestMatchers {@link GoyaRequest}
     * @param configAttributes 权限配置属性
     */
    private void writeToIndexable(GoyaRequest goyaRequest, List<GoyaConfigAttribute> configAttributes) {
        this.indexable.put(goyaRequest, configAttributes);
    }

    /**
     * 根据请求的 url 和 method 获取权限对象
     *
     * @param url    请求 URL
     * @param method 请求 method
     * @return 与请求url 和 method 匹配的权限数据，或者是空集合
     */
    public List<GoyaConfigAttribute> getConfigAttribute(String url, String method) {
        GoyaRequest goyaRequest = new GoyaRequest(url, method);
        return readFromIndexable(goyaRequest);
    }

    /**
     * 从 compatible 缓存中获取全部不需要路径匹配的（包含*号的url）请求权限映射Map
     *
     * @return 如果缓存中存在，则返回请求权限映射Map集合，如果不存在则返回一个空的{@link LinkedHashMap}
     */
    public LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> getCompatible() {
        return readFromCompatible();
    }

    /**
     * 向 compatible 缓存中添加需要路径匹配的（包含*号的url）请求权限映射Map。
     * <p>
     * 如果缓存中不存在以{@link RequestMatcher}为Key的数据，那么添加数据
     * 如果缓存中存在以{@link RequestMatcher}为Key的数据，那么合并数据
     *
     * @param goyaRequest 请求匹配对象 {@link GoyaRequest}
     * @param configAttributes 权限配置 {@link ConfigAttribute}
     */
    private void appendToCompatible(GoyaRequest goyaRequest, List<GoyaConfigAttribute> configAttributes) {
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> compatible = this.getCompatible();
//        compatible.merge(requestMatcher, configAttributes, (oldConfigAttributes, newConfigAttributes) -> {
//            newConfigAttributes.addAll(oldConfigAttributes);
//            return newConfigAttributes;
//        });

        // 使用merge会让整个功能的设计更加复杂，暂时改为直接覆盖已有数据，后续视情况再做变更。
        compatible.put(goyaRequest, configAttributes);
        log.trace("[Goya] |- Append [{}] to Compatible cache, current size is [{}]", goyaRequest, compatible.size());
        writeToCompatible(compatible);
    }

    /**
     * 向 compatible 缓存中添加需要路径匹配的（包含*号的url）请求权限映射Map。
     * <p>
     * 如果缓存中不存在以{@link RequestMatcher}为Key的数据，那么添加数据
     * 如果缓存中存在以{@link RequestMatcher}为Key的数据，那么合并数据
     *
     * @param configAttributes 请求权限映射Map
     */
    private void appendToCompatible(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> configAttributes) {
        configAttributes.forEach(this::appendToCompatible);
    }

    /**
     * 向 indexable 缓存中添加需请求权限映射。
     * <p>
     * 如果缓存中不存在以{@link GoyaRequest}为Key的数据，那么添加数据
     * 如果缓存中存在以{@link GoyaRequest}为Key的数据，那么合并数据
     *
     * @param goyaRequest 请求匹配对象 {@link GoyaRequest}
     * @param configAttributes 权限配置 {@link GoyaConfigAttribute}
     */
    private void appendToIndexable(GoyaRequest goyaRequest, List<GoyaConfigAttribute> configAttributes) {
        writeToIndexable(goyaRequest, configAttributes);
    }

    /**
     * 向 indexable 缓存中添加请求权限映射Map。
     *
     * @param configAttributes 请求权限映射Map
     */
    private void appendToIndexable(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> configAttributes) {
        configAttributes.forEach(this::appendToIndexable);
    }

    /**
     * 将权限数据添加至本地存储
     *
     * @param configAttributes 权限数据
     * @param isIndexable      true 存入 indexable cache；false 存入 compatible cache
     */
    public void addToStorage(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> configAttributes, boolean isIndexable) {
        if (MapUtils.isNotEmpty(configAttributes)) {
            if (isIndexable) {
                appendToIndexable(configAttributes);
            } else {
                appendToCompatible(configAttributes);
            }
        }
    }


    /**
     * 将权限数据添加至本地存储，存储之前进行规则冲突校验
     *
     * @param matchers         校验资源
     * @param configAttributes 权限数据
     * @param isIndexable      true 存入 indexable cache；false 存入 compatible cache
     */
    public void addToStorage(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> matchers, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> configAttributes, boolean isIndexable) {
        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> result = new LinkedHashMap<>();
        if (MapUtils.isNotEmpty(matchers) && MapUtils.isNotEmpty(configAttributes)) {
            result = checkConflict(matchers, configAttributes);
        }

        addToStorage(result, isIndexable);
    }

    /**
     * 规则冲突校验
     * <p>
     * 如存在规则冲突，则保留可支持最大化范围规则，冲突的其它规则则不保存
     *
     * @param matchers         校验资源
     * @param configAttributes 权限数据
     * @return 去除冲突的权限数据
     */
    private LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> checkConflict(LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> matchers, LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> configAttributes) {

        LinkedHashMap<GoyaRequest, List<GoyaConfigAttribute>> result = new LinkedHashMap<>(configAttributes);

        for (GoyaRequest matcher : matchers.keySet()) {
            for (GoyaRequest item : configAttributes.keySet()) {
                // 如果是修改的是占位符类型的接口的权限，同时 matchers 中也包含该占位符权限，那么就会因为配到而导致被删除，最终导致该接口的权限无法更新保存。
                // 例如：被检测请求为 /iot/product-category/{id}，而 matchers 中也存在 /iot/product-category/{id}，那么就会被从 result 中删掉。而导致无法更新 /iot/product-category/{id} 的权限
                if (!matcher.equals(item)) {
                    GoyaRequestMatcher requestMatcher = new GoyaRequestMatcher(matcher);
                    if (requestMatcher.matches(item)) {
                        result.remove(item);
                        log.trace("[Goya] |- Pattern [{}] is conflict with [{}], so remove it.", item.getPattern(), matcher.getPattern());
                    }
                }
            }
        }

        return result;
    }
}
