package com.ysmjjsy.goya.component.cache.jetcache.enhance;

import com.alicp.jetcache.Cache;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;


/**
 * <p>Description: 基于 JetCache 的 Spring Cache 扩展 </p>
 *
 * @author goya
 * @since 2022/7/23 11:11
 */
public class JetCacheSpringCache extends AbstractValueAdaptingCache {

    private static final Logger log = LoggerFactory.getLogger(JetCacheSpringCache.class);

    private final String cacheName;
    private final Cache<Object, Object> cache;

    public JetCacheSpringCache(String cacheName, Cache<Object, Object> cache, boolean allowNullValues) {
        super(allowNullValues);
        this.cacheName = cacheName;
        this.cache = cache;
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public final Cache<Object, Object> getNativeCache() {
        return this.cache;
    }

    @Override
    @Nullable
    protected Object lookup(Object key) {
        Object value = cache.get(key);
        if (ObjectUtils.isNotEmpty(value)) {
            log.trace("[Goya] |- CACHE - Lookup data in goya cache, value is : [{}]", value);
            return value;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {

        log.trace("[Goya] |- CACHE - Get data in goya cache, key: {}", key);

        return (T) fromStoreValue(cache.computeIfAbsent(key, k -> {
            try {
                return toStoreValue(valueLoader.call());
            } catch (Throwable ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            }
        }));
    }

    @Override
    @Nullable
    public void put(Object key, @Nullable Object value) {
        log.trace("[Goya] |- CACHE - Put data in goya cache, key: {}", key);
        cache.put(key, this.toStoreValue(value));
    }


    @Override
    @Nullable
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        log.trace("[Goya] |- CACHE - PutIfPresent data in goya cache, key: {}", key);
        Object existing = cache.putIfAbsent(key, toStoreValue(value));
        return toValueWrapper(existing);
    }

    @Override
    public void evict(Object key) {
        log.trace("[Goya] |- CACHE - Evict data in goya cache, key: {}", key);
        cache.remove(key);
    }

    @Override
    public boolean evictIfPresent(Object key) {
        log.trace("[Goya] |- CACHE - EvictIfPresent data in goya cache, key: {}", key);
        return cache.remove(key);
    }

    @Override
    public void clear() {
        log.trace("[Goya] |- CACHE - Clear data in goya cache.");
        cache.close();
    }
}