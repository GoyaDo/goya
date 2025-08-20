package com.ysmjjsy.goya.module.redis.utils;

import com.ysmjjsy.goya.component.cache.api.CacheGetFilter;
import com.ysmjjsy.goya.component.cache.api.CacheGetIfAbsent;
import com.ysmjjsy.goya.component.cache.api.CacheLoader;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.module.redis.definition.GoyaRedisCache;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.redisson.api.RBloomFilter;

import java.time.Duration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 00:51
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisUtils {

    private static class Holder {
        static final GoyaRedisCache distributedCache =
                SpringContextHolder.getBean(GoyaRedisCache.class);
    }

    private static GoyaRedisCache distributedCache() {
        return RedisUtils.Holder.distributedCache;
    }

    /**
     * 获取缓存，如查询结果为空，调用 {@link CacheLoader} 加载缓存
     */
    public static <T> T get(@NotBlank String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout) {
        return distributedCache().get(key, clazz, cacheLoader, timeout);
    }

    /**
     * 以一种"安全"的方式获取缓存，如查询结果为空，调用 {@link CacheLoader} 加载缓存
     * 通过此方式防止程序中可能出现的：缓存击穿、缓存雪崩场景，适用于不被外部直接调用的接口
     */
    public static <T> T safeGet(@NotBlank String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout) {
        return distributedCache().safeGet(key, clazz, cacheLoader, timeout);
    }

    /**
     * 以一种"安全"的方式获取缓存，如查询结果为空，调用 {@link CacheLoader} 加载缓存
     * 通过此方式防止程序中可能出现的：缓存穿透、缓存击穿以及缓存雪崩场景，需要客户端传递布隆过滤器，适用于被外部直接调用的接口
     */
    public static <T> T safeGet(@NotBlank String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout, RBloomFilter<String> bloomFilter) {
        return distributedCache().safeGet(key, clazz, cacheLoader, timeout, bloomFilter);
    }

    /**
     * 以一种"安全"的方式获取缓存，如查询结果为空，调用 {@link CacheLoader} 加载缓存
     * 通过此方式防止程序中可能出现的：缓存穿透、缓存击穿以及缓存雪崩场景，需要客户端传递布隆过滤器，并通过 {@link CacheGetFilter} 解决布隆过滤器无法删除问题，适用于被外部直接调用的接口
     */
    public static <T> T safeGet(@NotBlank String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout, RBloomFilter<String> bloomFilter, CacheGetFilter<String> cacheCheckFilter) {
        return distributedCache().safeGet(key, clazz, cacheLoader, timeout, bloomFilter, cacheCheckFilter);
    }

    /**
     * 以一种"安全"的方式获取缓存，如查询结果为空，调用 {@link CacheLoader} 加载缓存
     * 通过此方式防止程序中可能出现的：缓存穿透、缓存击穿以及缓存雪崩场景，需要客户端传递布隆过滤器，并通过 {@link CacheGetFilter} 解决布隆过滤器无法删除问题，适用于被外部直接调用的接口
     */
    public static <T> T safeGet(@NotBlank String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout,
                                RBloomFilter<String> bloomFilter, CacheGetFilter<String> cacheCheckFilter, CacheGetIfAbsent<String> cacheGetIfAbsent) {
        return distributedCache().safeGet(key, clazz, cacheLoader, timeout, bloomFilter, cacheCheckFilter, cacheGetIfAbsent);
    }

    /**
     * 放入缓存，自定义超时时间
     */
    public static void put(@NotBlank String key, Object value, Duration timeout) {
        distributedCache().put(key, value, timeout);
    }

    /**
     * 放入缓存，自定义超时时间
     * 通过此方式防止程序中可能出现的：缓存穿透、缓存击穿以及缓存雪崩场景，需要客户端传递布隆过滤器，适用于被外部直接调用的接口
     */
    public static void safePut(@NotBlank String key, Object value, Duration timeout, RBloomFilter<String> bloomFilter) {
        distributedCache().safePut(key, value, timeout, bloomFilter);
    }

    /**
     * 统计指定 key 的存在数量
     */
    public static Long countExistingKeys(@NotNull String... keys) {
        return distributedCache().countExistingKeys(keys);
    }
}
