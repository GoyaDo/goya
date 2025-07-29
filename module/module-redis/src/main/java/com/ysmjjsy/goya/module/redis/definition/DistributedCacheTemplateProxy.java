package com.ysmjjsy.goya.module.redis.definition;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.ysmjjsy.goya.component.cache.configuration.properties.CacheProperties;
import com.ysmjjsy.goya.component.cache.core.CacheGetFilter;
import com.ysmjjsy.goya.component.cache.core.CacheGetIfAbsent;
import com.ysmjjsy.goya.component.cache.core.CacheLoader;
import com.ysmjjsy.goya.component.common.strategy.Singleton;
import com.ysmjjsy.goya.component.json.jackson2.utils.Jackson2Utils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 00:25
 */
@RequiredArgsConstructor
public class DistributedCacheTemplateProxy implements DistributedCache {

    private final StringRedisTemplate stringRedisTemplate;
    private final CacheProperties cacheProperties;
    private final RedissonClient redissonClient;

    private static final String LUA_PUT_IF_ALL_ABSENT_SCRIPT_PATH = "lua/putIfAllAbsent.lua";
    private static final String SAFE_GET_DISTRIBUTED_LOCK_KEY_PREFIX = "safe_get_distributed_lock_get:";

    @Override
    public <T> T get(String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout) {
        T result = get(key, clazz);
        if (!isNullOrBlank(result)) {
            return result;
        }
        return loadAndSet(key, cacheLoader, cacheProperties.expire(), false, null);
    }

    @Override
    public <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout) {
        return safeGet(key, clazz, cacheLoader, cacheProperties.expire(), null);
    }

    @Override
    public <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout, RBloomFilter<String> bloomFilter) {
        return safeGet(key, clazz, cacheLoader, cacheProperties.expire(), bloomFilter, null);
    }

    @Override
    public <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout, RBloomFilter<String> bloomFilter, CacheGetFilter<String> cacheGetFilter) {
        return safeGet(key, clazz, cacheLoader, cacheProperties.expire(), bloomFilter, cacheGetFilter, null);
    }

    @Override
    public <T> T safeGet(String key, Class<T> clazz, CacheLoader<T> cacheLoader, Duration timeout, RBloomFilter<String> bloomFilter, CacheGetFilter<String> cacheGetFilter, CacheGetIfAbsent<String> cacheGetIfAbsent) {
        T result = get(key, clazz);
        // 缓存结果不等于空或空字符串直接返回；通过函数判断是否返回空，为了适配布隆过滤器无法删除的场景；两者都不成立，判断布隆过滤器是否存在，不存在返回空
        if (!isNullOrBlank(result)
                || Optional.ofNullable(cacheGetFilter).map(each -> each.filter(key)).orElse(false)
                || Optional.ofNullable(bloomFilter).map(each -> !each.contains(key)).orElse(false)) {
            return result;
        }
        RLock lock = redissonClient.getLock(SAFE_GET_DISTRIBUTED_LOCK_KEY_PREFIX + key);
        lock.lock();
        try {
            // 双重判定锁，减轻获得分布式锁后线程访问数据库压力
            if (isNullOrBlank(result = get(key, clazz)) && isNullOrBlank(result = loadAndSet(key, cacheLoader, timeout, true, bloomFilter))) {
                Optional.ofNullable(cacheGetIfAbsent).ifPresent(each -> each.execute(key));
            }

        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public void put(String key, Object value, Duration timeout) {
        String actual = value instanceof String ca ? ca : Jackson2Utils.toJson(value);
        stringRedisTemplate.opsForValue().set(key, actual, timeout);
    }

    @Override
    public void safePut(String key, Object value, Duration timeout, RBloomFilter<String> bloomFilter) {
        put(key, value, timeout);
        bloomFilter.add(key);
    }

    @Override
    public Long countExistingKeys(@NotNull String... keys) {
        return stringRedisTemplate.countExistingKeys(Lists.newArrayList(keys));
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (String.class.isAssignableFrom(clazz)) {
            return (T) value;
        }
        return Jackson2Utils.toObject(value, clazz);
    }

    @Override
    public void put(String key, Object value) {
        put(key, value, cacheProperties.expire());
    }

    @Override
    public Boolean putIfAllAbsent(Collection<String> keys) {
        DefaultRedisScript<Boolean> actual = Singleton.get(LUA_PUT_IF_ALL_ABSENT_SCRIPT_PATH, () -> {
            DefaultRedisScript redisScript = new DefaultRedisScript();
            redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(LUA_PUT_IF_ALL_ABSENT_SCRIPT_PATH)));
            redisScript.setResultType(Boolean.class);
            return redisScript;
        });
        return stringRedisTemplate.execute(actual, Lists.newArrayList(keys), cacheProperties.expire());
    }

    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public Long delete(Collection<String> keys) {
        return stringRedisTemplate.delete(keys);
    }

    @Override
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Object getInstance() {
        return stringRedisTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    private <T> T loadAndSet(String key, CacheLoader<T> cacheLoader, Duration timeout, boolean safeFlag, RBloomFilter<String> bloomFilter) {
        T result = cacheLoader.load();
        if (isNullOrBlank(result)) {
            return result;
        }
        if (safeFlag) {
            safePut(key, result, timeout, bloomFilter);
        } else {
            put(key, result, timeout);
        }
        return result;
    }

    /**
     * 判断结果是否为空或空的字符串
     *
     * @param cacheVal cacheVal
     * @return 结果
     */
    private static boolean isNullOrBlank(Object cacheVal) {
        return cacheVal == null || (cacheVal instanceof String ca && Strings.isNullOrEmpty(ca));
    }
}
