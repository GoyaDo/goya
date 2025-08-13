package com.ysmjjsy.goya.component.cache.utils;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.ysmjjsy.goya.component.cache.core.jetcache.JetCacheCreateCacheFactory;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import lombok.experimental.UtilityClass;

import java.time.Duration;

/**
 * <p>Description: JetCache 单例工具类 </p>
 *
 * @author goya
 * @since 2022/8/9 15:43
 */
@UtilityClass
public class JetCacheUtils {

    public static <K, V> Cache<K, V> create(String name, Duration expire) {
        return create(name, expire, true);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue) {
        return create(name, expire, cacheNullValue, null);
    }

    public static <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(name, CacheType.BOTH, expire, cacheNullValue, syncLocal);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType) {
        return create(name, cacheType, null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire) {
        return create(name, cacheType, expire, true);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue) {
        return create(name, cacheType, expire, cacheNullValue, null);
    }

    public static <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return SpringContextHolder.getBean(JetCacheCreateCacheFactory.class).create(name, cacheType, expire, cacheNullValue, syncLocal);
    }
}
