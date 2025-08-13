package com.ysmjjsy.goya.component.cache.api;

/**
 * <p>缓存过滤</p>
 *
 * @author goya
 * @since 2025/7/30 00:17
 */
@FunctionalInterface
public interface CacheGetFilter<T> {

    /**
     * 缓存过滤
     *
     * @param param 输出参数
     * @return {@code true} 如果输入参数匹配，否则 {@link Boolean#TRUE}
     */
    boolean filter(T param);
}
