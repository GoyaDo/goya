package com.ysmjjsy.goya.component.cache.utils;

import com.ysmjjsy.goya.component.cache.api.CacheApi;
import com.ysmjjsy.goya.component.cache.core.DefaultCacheImpl;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import lombok.experimental.UtilityClass;

import java.util.Collection;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 19:54
 */
@UtilityClass
public class CacheUtils {

    /**
     * 获取缓存
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        return getCache().get(key, clazz);
    }

    /**
     * 放入缓存
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        getCache().put(key, value);
    }

    /**
     * 如果 keys 全部不存在，则新增，返回 true，反之 false
     *
     * @param keys
     * @return
     */
    public Boolean putIfAllAbsent(Collection<String> keys) {
        return getCache().putIfAllAbsent(keys);
    }

    /**
     * 删除缓存
     *
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return getCache().delete(key);
    }

    /**
     * 删除 keys，返回删除数量
     *
     * @param keys
     * @return
     */
    public Long delete(Collection<String> keys) {
        return getCache().delete(keys);
    }

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        return getCache().hasKey(key);
    }

    public static CacheApi getCache() {
        return SpringContextHolder.getBean(DefaultCacheImpl.class);
    }
}
