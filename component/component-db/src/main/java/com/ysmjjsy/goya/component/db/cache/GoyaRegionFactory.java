package com.ysmjjsy.goya.component.db.cache;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.cache.spi.support.RegionFactoryTemplate;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.cache.CacheManager;

import java.util.Map;

/**
 * <p>Description: 自定义Hibernate二级缓存RegionFactory </p>
 *
 * @author goya
 * @since 2021/7/12 22:04
 */
public class GoyaRegionFactory extends RegionFactoryTemplate {

    private CacheManager cacheManager;

    @Override
    protected StorageAccess createQueryResultsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new GoyaDomainDataStorageAccess(cacheManager.getCache(regionName));
    }

    @Override
    protected StorageAccess createTimestampsRegionStorageAccess(String regionName, SessionFactoryImplementor sessionFactory) {
        return new GoyaDomainDataStorageAccess(cacheManager.getCache(regionName));
    }

    @Override
    protected DomainDataStorageAccess createDomainDataStorageAccess(DomainDataRegionConfig regionConfig, DomainDataRegionBuildingContext buildingContext) {
        return new GoyaDomainDataStorageAccess(cacheManager.getCache(regionConfig.getRegionName()));
    }

    @Override
    protected void prepareForUse(SessionFactoryOptions settings, Map configValues) {
        this.cacheManager = SpringUtil.getBean("goyaCacheManager");
    }

    @Override
    protected void releaseFromUse() {
        cacheManager = null;
    }
}
