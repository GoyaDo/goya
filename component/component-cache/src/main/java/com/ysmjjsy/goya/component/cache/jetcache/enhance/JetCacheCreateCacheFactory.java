package com.ysmjjsy.goya.component.cache.jetcache.enhance;

import com.alibaba.fastjson2.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import com.ysmjjsy.goya.component.cache.properties.CacheProperties;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;

import java.time.Duration;
/**
 * <p>Description: JetCache 手动创建Cache 工厂 </p>
 *
 * @author goya
 * @since 2022/7/23 10:49
 */
public class JetCacheCreateCacheFactory {

    private final CacheManager cacheManager;
    private final CacheProperties cacheProperties;

    public JetCacheCreateCacheFactory(CacheManager cacheManager, CacheProperties cacheProperties) {
        this.cacheManager = cacheManager;
        this.cacheProperties = cacheProperties;
    }

    public <K, V> Cache<K, V> create(String name, CacheProperties cacheProperties) {
        return create(name, false, cacheProperties);
    }

    public <K, V> Cache<K, V> create(String name, Boolean cacheNullValue, CacheProperties cacheProperties) {
        return create(cacheProperties.area(),
                name,
                cacheProperties.cacheType(),
                cacheProperties.expire(),
                cacheNullValue,
                cacheProperties.sync(),
                cacheProperties.localExpire(),
                cacheProperties.localLimit(),
                false,
                cacheProperties.penetrationProtect(),
                cacheProperties.penetrationProtectTimeout());
    }

    public <K, V> Cache<K, V> create(String name) {
        return create(name, cacheProperties.expire());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire) {
        return create(name, expire, cacheProperties.allowNullValues());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue) {
        return create(name, expire, cacheNullValue, cacheProperties.sync());
    }

    public <K, V> Cache<K, V> create(String name, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(name, cacheProperties.cacheType(), expire, cacheNullValue, syncLocal);
    }

    public <K, V> Cache<K, V> create(String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(null, name, cacheType, expire, cacheNullValue, syncLocal);
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, cacheProperties.localExpire());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, cacheProperties.localLimit());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, localLimit, false);
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit, Boolean useAreaInPrefix) {
        return create(area, name, cacheType, expire, cacheNullValue, syncLocal, localExpire, localLimit, useAreaInPrefix, cacheProperties.penetrationProtect(), cacheProperties.penetrationProtectTimeout());
    }

    public <K, V> Cache<K, V> create(String area, String name, CacheType cacheType, Duration expire, Boolean cacheNullValue, Boolean syncLocal, Duration localExpire, Integer localLimit, Boolean useAreaInPrefix, Boolean penetrationProtect, Duration penetrationProtectTimeout) {
        QuickConfig.Builder builder = StringUtils.isEmpty(area) ? QuickConfig.newBuilder(name) : QuickConfig.newBuilder(area, name);
        builder.cacheType(cacheType);
        builder.expire(expire);
        if (cacheType == CacheType.BOTH) {
            builder.syncLocal(syncLocal);
        }
        builder.localExpire(localExpire);
        builder.localLimit(localLimit);
        builder.cacheNullValue(cacheNullValue);
        builder.useAreaInPrefix(useAreaInPrefix);
        if (ObjectUtils.isNotEmpty(penetrationProtect)) {
            builder.penetrationProtect(penetrationProtect);
            if (BooleanUtils.isTrue(penetrationProtect) && ObjectUtils.isNotEmpty(penetrationProtectTimeout)) {
                builder.penetrationProtectTimeout(penetrationProtectTimeout);
            }
        }

        builder.keyConvertor(key -> {
            if (key instanceof String) {
                return key;
            } else {
                return SecureUtil.md5(JSON.toJSONString(key));
            }
        });

        QuickConfig quickConfig = builder.build();
        return create(quickConfig);
    }


    @SuppressWarnings("unchecked")
    private <K, V> Cache<K, V> create(QuickConfig quickConfig) {
        return cacheManager.getOrCreateCache(quickConfig);
    }

}