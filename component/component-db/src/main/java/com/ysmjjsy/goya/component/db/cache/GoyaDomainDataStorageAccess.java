package com.ysmjjsy.goya.component.db.cache;

import com.ysmjjsy.goya.component.common.context.TenantContextHolder;
import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;
import org.hibernate.cache.spi.QueryKey;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;

/**
 * <p>Description: 自定义Hibernate二级缓存DomainDataStorageAccess </p>
 *
 * @author goya
 * @since 2021/7/12 22:06
 */
public class GoyaDomainDataStorageAccess implements DomainDataStorageAccess {

    private static final Logger log = LoggerFactory.getLogger(GoyaDomainDataStorageAccess.class);

    private Cache cache;

    public GoyaDomainDataStorageAccess() {
    }

    public GoyaDomainDataStorageAccess(Cache cache) {
        this.cache = cache;
    }

    private String secure(Object key) {
        if (key instanceof QueryKey queryKey) {
            int hashCode = queryKey.hashCode();
            String hashCodeString = String.valueOf(hashCode);
            String secureKey = SecureUtil.md5(hashCodeString);
            log.trace("[Goya] |- SPI - Convert query key hashcode [{}] to secureKey [{}]", hashCode, secureKey);
            return secureKey;
        }
        return String.valueOf(key);
    }

    private String getTenantId() {
        String tenantId = TenantContextHolder.getTenantId();
        log.trace("[Goya] |- SPI - Tenant identifier for jpa second level cache is : [{}]", tenantId);
        return StringUtils.toRootLowerCase(tenantId);
    }

    private String wrapper(Object key) {
        String original = secure(key);
        String tenantId = getTenantId();

        String result = tenantId + SymbolConstants.COLON + original;
        log.trace("[Goya] |- SPI - Current cache key is : [{}]", result);
        return result;
    }

    private Object get(Object key) {
        Cache.ValueWrapper value = cache.get(key);

        if (ObjectUtils.isNotEmpty(value)) {
            return value.get();
        }
        return null;
    }

    @Override
    public boolean contains(Object key) {
        String wrapperKey = wrapper(key);
        Object value = this.get(wrapperKey);
        log.trace("[Goya] |- SPI - check is key : [{}] exist.", wrapperKey);
        return ObjectUtils.isNotEmpty(value);
    }

    @Override
    public Object getFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        Object value = this.get(wrapperKey);
        log.trace("[Goya] |- SPI - get from cache key is : [{}], value is : [{}]", wrapperKey, value);
        return value;
    }

    @Override
    public void putIntoCache(Object key, Object value, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Goya] |- SPI - put into cache key is : [{}], value is : [{}]", wrapperKey, value);
        cache.put(wrapperKey, value);
    }

    @Override
    public void removeFromCache(Object key, SharedSessionContractImplementor session) {
        String wrapperKey = wrapper(key);
        log.trace("[Goya] |- SPI - remove from cache key is : [{}]", wrapperKey);
        cache.evict(wrapperKey);
    }

    @Override
    public void evictData(Object key) {
        String wrapperKey = wrapper(key);
        log.trace("[Goya] |- SPI - evict key : [{}] from cache.", wrapperKey);
        cache.evict(wrapperKey);
    }

    @Override
    public void clearCache(SharedSessionContractImplementor session) {
        this.evictData();
    }

    @Override
    public void evictData() {
        cache.clear();
    }

    @Override
    public void release() {
        cache.invalidate();
    }
}
