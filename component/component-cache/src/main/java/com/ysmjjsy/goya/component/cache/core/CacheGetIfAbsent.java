package com.ysmjjsy.goya.component.cache.core;

/**
 * <p>缓存查询为空</p>
 *
 * @author goya
 * @since 2025/7/30 00:18
 */
@FunctionalInterface
public interface CacheGetIfAbsent<T> {

    /**
     * 如果查询结果为空，执行逻辑
     *
     * @param param
     */
    void execute(T param);
}
