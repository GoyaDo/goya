package com.ysmjjsy.goya.component.cache.core;

import com.ysmjjsy.goya.component.cache.api.CacheApi;
import com.ysmjjsy.goya.component.cache.constants.CacheConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Objects;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 21:22
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultCacheImpl implements CacheApi {

    private final CacheManager cacheManager;
    private Cache defaultCache;

    private Cache getDefaultCache() {
        if (defaultCache == null) {
            defaultCache = cacheManager.getCache(CacheConstants.CACHE_SIMPLE_BASE_PREFIX);
        }
        return defaultCache;
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. key: {}", key);
            return null;
        }

        try {
            Cache.ValueWrapper wrapper = cache.get(key);
            if (wrapper == null) {
                return null;
            }
            Object value = wrapper.get();
            if (value == null) {
                return null;
            }
            if (clazz == null || clazz.isInstance(value)) {
                @SuppressWarnings("unchecked")
                T cast = (T) value;
                return cast;
            }
            // 尝试通过 Spring Cache 的类型化 get（若底层实现支持）
            try {
                return cache.get(key, clazz);
            } catch (Exception ignore) {
                log.debug("[Goya] |- CACHE - Type cast failed for key: {}, expected: {}, actual: {}", key, clazz, value.getClass());
                return null;
            }
        } catch (Throwable ex) {
            log.error("[Goya] |- CACHE - Get data failed. key: {}", key, ex);
            return null;
        }
    }

    @Override
    public void put(String key, Object value) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. skip put. key: {}", key);
            return;
        }
        try {
            cache.put(key, value);
        } catch (Exception ex) {
            log.error("[Goya] |- CACHE - Put data failed. key: {}", key, ex);
        }
    }

    @Override
    public Boolean putIfAllAbsent(Collection<String> keys) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. skip putIfAllAbsent.");
            return Boolean.FALSE;
        }
        if (keys == null || keys.isEmpty()) {
            return Boolean.TRUE;
        }

        // 非原子实现：先检查全部不存在，再批量写入占位值
        for (String key : keys) {
            if (key == null) {
                continue;
            }
            if (Objects.nonNull(cache.get(key))) {
                return Boolean.FALSE;
            }
        }
        for (String key : keys) {
            if (key == null) {
                continue;
            }
            cache.put(key, Boolean.TRUE);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean delete(String key) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. skip delete. key: {}", key);
            return Boolean.FALSE;
        }
        try {
            return cache.evictIfPresent(key);
        } catch (Exception ex) {
            log.error("[Goya] |- CACHE - Delete data failed. key: {}", key, ex);
            return Boolean.FALSE;
        }
    }

    @Override
    public Long delete(Collection<String> keys) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. skip batch delete.");
            return 0L;
        }
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }
        long deleted = 0L;
        for (String key : keys) {
            try {
                if (cache.evictIfPresent(key)) {
                    deleted++;
                }
            } catch (Exception ex) {
                log.error("[Goya] |- CACHE - Delete data failed. key: {}", key, ex);
            }
        }
        return deleted;
    }

    @Override
    public Boolean hasKey(String key) {
        Cache cache = getDefaultCache();
        if (cache == null) {
            log.warn("[Goya] |- CACHE - Default cache is not available. hasKey -> false. key: {}", key);
            return Boolean.FALSE;
        }
        try {
            return cache.get(key) != null;
        } catch (Exception ex) {
            log.error("[Goya] |- CACHE - HasKey failed. key: {}", key, ex);
            return Boolean.FALSE;
        }
    }

    @Override
    public Object getInstance() {
        Cache cache = getDefaultCache();
        if (cache != null) {
            try {
                return cache.getNativeCache();
            } catch (Exception ignore) {
                return cache;
            }
        }
        return cacheManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.defaultCache = cacheManager.getCache(CacheConstants.CACHE_SIMPLE_BASE_PREFIX);
        if (this.defaultCache == null) {
            log.warn("[Goya] |- CACHE - Create default cache failed for name: {}", CacheConstants.CACHE_SIMPLE_BASE_PREFIX);
        } else {
            log.debug("[Goya] |- CACHE - Default cache [{}] is ready.", CacheConstants.CACHE_SIMPLE_BASE_PREFIX);
        }
    }
}
