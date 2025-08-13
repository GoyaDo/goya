package com.ysmjjsy.goya.component.cache.stamp;

import com.alicp.jetcache.AutoReleaseLock;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.ysmjjsy.goya.component.cache.api.CacheStampApi;
import com.ysmjjsy.goya.component.cache.utils.JetCacheUtils;
import com.ysmjjsy.goya.component.common.exception.stamp.StampHasExpiredException;
import com.ysmjjsy.goya.component.common.exception.stamp.StampMismatchException;
import com.ysmjjsy.goya.component.common.exception.stamp.StampParameterIllegalException;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 抽象Stamp管理 </p>
 *
 * @param <K> 签章缓存对应Key值的类型。
 * @param <V> 签章缓存存储数据，对应的具体存储值的类型，
 * @author goya
 * @since 2021/8/23 11:51
 */
public abstract class AbstractStampManager<K, V> implements CacheStampApi<K, V> {

    private static final Logger log = LoggerFactory.getLogger(AbstractStampManager.class);

    private static final Duration DEFAULT_EXPIRE = Duration.ofMinutes(30);

    @Setter
    private Duration expire;
    private final Cache<K, V> cache;

    protected AbstractStampManager(String cacheName) {
        this(cacheName, CacheType.BOTH);
    }

    protected AbstractStampManager(String cacheName, CacheType cacheType) {
        this(cacheName, cacheType, DEFAULT_EXPIRE);
    }

    protected AbstractStampManager(String cacheName, CacheType cacheType, Duration expire) {
        this.expire = expire;
        this.cache = JetCacheUtils.create(cacheName, cacheType, this.expire);
    }

    /**
     * 指定数据存储缓存
     *
     * @return {@link Cache}
     */
    protected Cache<K, V> getCache() {
        return this.cache;
    }

    @Override
    public Duration getExpire() {
        return this.expire;
    }

    @Override
    public boolean check(K key, V value) throws StampParameterIllegalException, StampHasExpiredException, StampMismatchException {

        if (ObjectUtils.isEmpty(value)) {
            throw new StampParameterIllegalException("Parameter Stamp value is null");
        }

        V storedStamp = this.get(key);
        if (ObjectUtils.isEmpty(storedStamp)) {
            throw new StampHasExpiredException("Stamp is invalid!");
        }

        if (ObjectUtils.notEqual(storedStamp, value)) {
            throw new StampMismatchException("Stamp is mismathch!");
        }

        return true;
    }

    @Override
    public V get(K key) {
        return this.getCache().get(key);
    }

    @Override
    public void delete(K key) {
        boolean result = this.getCache().remove(key);

        if (!result) {
            log.warn("[Goya] |- Delete stamp [{}] failed.", key);
        }
    }

    @Override
    public void put(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        this.getCache().put(key, value, expireAfterWrite, timeUnit);
    }

    @Override
    public AutoReleaseLock lock(K key, long expire, TimeUnit timeUnit) {
        return this.getCache().tryLock(key, expire, timeUnit);
    }

    @Override
    public boolean lockAndRun(K key, long expire, TimeUnit timeUnit, Runnable action) {
        return this.getCache().tryLockAndRun(key, expire, timeUnit, action);
    }
}