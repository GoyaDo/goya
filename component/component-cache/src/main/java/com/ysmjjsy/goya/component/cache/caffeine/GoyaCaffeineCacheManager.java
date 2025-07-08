package com.ysmjjsy.goya.component.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ysmjjsy.goya.component.cache.properties.CacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/**
 * <p>Description: 扩展的 CaffeineCacheManager </p>
 * <p>
 * 用于支持 Caffeine 缓存可以针对实体进行单独的过期时间设定
 *
 * @author goya
 * @since 2021/10/25 18:12
 */
public class GoyaCaffeineCacheManager extends CaffeineCacheManager {

    private static final Logger log = LoggerFactory.getLogger(GoyaCaffeineCacheManager.class);

    private final CacheProperties cacheProperties;

    public GoyaCaffeineCacheManager(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.allowNullValues());
    }

    public GoyaCaffeineCacheManager(CacheProperties cacheProperties, String... cacheNames) {
        super(cacheNames);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.allowNullValues());
    }

    @Override
    protected Cache<Object, Object> createNativeCaffeineCache(String name) {
        log.debug("[Goya] |- CACHE - Caffeine cache [{}] is set to use INSTANCE config.", name);
        return Caffeine.newBuilder().expireAfterWrite(cacheProperties.expire()).build();
    }
}
