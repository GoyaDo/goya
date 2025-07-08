package com.ysmjjsy.goya.component.cache.jetcache.enhance;

import com.ysmjjsy.goya.component.cache.properties.CacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * <p>Description: 自定义 缓存管理器 </p>
 *
 * @author goya
 * @since 2022/7/23 17:02
 */
public class GoyaCacheManager extends JetCacheSpringCacheManager {

    private static final Logger log = LoggerFactory.getLogger(GoyaCacheManager.class);

    private final CacheProperties cacheProperties;

    public GoyaCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        super(jetCacheCreateCacheFactory);
        this.cacheProperties = cacheProperties;
        this.setAllowNullValues(cacheProperties.allowNullValues());
    }

    public GoyaCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties, String... cacheNames) {
        super(jetCacheCreateCacheFactory, cacheNames);
        this.cacheProperties = cacheProperties;
    }

    @Override
    protected Cache createJetCache(String name) {
        log.debug("[Goya] |- CACHE - Cache [{}] is set to use INSTANCE cache.", name);
        return super.createJetCache(name, cacheProperties);
    }
}