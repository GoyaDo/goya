package com.ysmjjsy.goya.component.cache.core;

import com.ysmjjsy.goya.component.cache.jetcache.enhance.GoyaCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.Collection;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 21:22
 */
@Slf4j
@RequiredArgsConstructor
public class GoyaDefaultCache implements GoyaCache {

    private final GoyaCacheManager goyaCacheManager;

    @Override
    public <T> T get(String key, Class<T> clazz) {
        Cache cache = goyaCacheManager.getCache(key);
        if (cache == null) {
            return null;
        }
        return cache.get(key, clazz);
    }

    @Override
    public void put(String key, Object value) {
        Cache cache = goyaCacheManager.getCache(key);
        cache.put(key, value);
    }

    @Override
    public Boolean putIfAllAbsent(Collection<String> keys) {
        for (String key : keys) {
            if (goyaCacheManager.getCache(key) == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean delete(String key) {
        goyaCacheManager.getCache(key).clear();
        return true;
    }

    @Override
    public Long delete(Collection<String> keys) {
        keys.forEach(key -> goyaCacheManager.getCache(key).clear());
        return (long) keys.size();
    }

    @Override
    public Boolean hasKey(String key) {
        return goyaCacheManager.getCache(key) != null;
    }

    @Override
    public Object getInstance() {
        return goyaCacheManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
