package com.ysmjjsy.goya.component.cache.utils;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.ysmjjsy.goya.component.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

/**
 * <p>Description: JetCache 单例工具类 </p>
 *
 * @author goya
 * @since 2022/8/9 15:43
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JetCacheUtils {

    private static class Holder {
        static final JetCacheCreateCacheFactory jetCacheCreateCacheFactory =
                ApplicationContextHolder.getBean(JetCacheCreateCacheFactory.class);
    }

    private static JetCacheCreateCacheFactory jetCacheCreateCacheFactory() {
        return Holder.jetCacheCreateCacheFactory;
    }

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
        return jetCacheCreateCacheFactory().create(name, cacheType, expire, cacheNullValue, syncLocal);
    }
}