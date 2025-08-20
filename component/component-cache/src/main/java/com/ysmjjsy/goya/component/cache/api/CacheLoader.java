package com.ysmjjsy.goya.component.cache.api;

/**
 * <p>缓存加载器</p>
 *
 * @author goya
 * @since 2025/7/30 00:18
 */
@FunctionalInterface
public interface CacheLoader<T> {

    /**
     * 加载缓存
     *
     * @return
     */
    T load();
}
