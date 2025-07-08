package com.ysmjjsy.goya.component.cache.configuration;

import com.alicp.jetcache.CacheManager;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ysmjjsy.goya.component.cache.caffeine.GoyaCaffeineCacheManager;
import com.ysmjjsy.goya.component.cache.jetcache.enhance.GoyaCacheManager;
import com.ysmjjsy.goya.component.cache.jetcache.enhance.JetCacheCreateCacheFactory;
import com.ysmjjsy.goya.component.cache.properties.CacheProperties;
import com.ysmjjsy.goya.component.cache.utils.JetCacheUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 16:19
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass({CacheManager.class})
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration {

    private final CacheProperties cacheProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [cache] configure.");
    }

    @Bean
    public Caffeine<Object, Object> caffeine() {
        Caffeine<Object, Object> caffeine = Caffeine
                .newBuilder()
                .expireAfterWrite(cacheProperties.expire());

        log.trace("[Goya] |- Bean [Caffeine] Configure.");

        return caffeine;
    }

    /**
     * 环境中如果包含了 Caffeine 依赖以及配置了 {@link CaffeineCacheManager} Bean，就会开启 Spring Cloud LoadBalancer 缓存。这在生产环境中可以提升 LoadBalancer 性能。
     * Spring Cloud LoadBalancer 推荐缓存为  Caffeine
     * 参见 <code>org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration</code>
     *
     * @param caffeine Caffeine 缓存
     * @return {@link CaffeineCacheManager}
     */
    @Bean
    @ConditionalOnMissingBean
    public CaffeineCacheManager caffeineCacheManager(Caffeine<Object, Object> caffeine) {
        GoyaCaffeineCacheManager goyaCaffeineCacheManager = new GoyaCaffeineCacheManager(cacheProperties);
        goyaCaffeineCacheManager.setCaffeine(caffeine);
        log.trace("[Goya] |- Bean [Caffeine Cache Manager] Configure.");
        return goyaCaffeineCacheManager;
    }

    @Bean
    public JetCacheCreateCacheFactory jetCacheCreateCacheFactory(@Qualifier("jcCacheManager") CacheManager cacheManager, CacheProperties cacheProperties) {
        JetCacheCreateCacheFactory factory = new JetCacheCreateCacheFactory(cacheManager, cacheProperties);
        JetCacheUtils.setJetCacheCreateCacheFactory(factory);
        log.trace("[Goya] |- Bean [Jet Cache Create Cache Factory] Configure.");
        return factory;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public GoyaCacheManager goyaCacheManager(JetCacheCreateCacheFactory jetCacheCreateCacheFactory, CacheProperties cacheProperties) {
        GoyaCacheManager goyaCacheManager = new GoyaCacheManager(jetCacheCreateCacheFactory, cacheProperties);
        goyaCacheManager.setAllowNullValues(cacheProperties.allowNullValues());
        log.trace("[Goya] |- Bean [Jet Cache Goya Cache Manager] Configure.");
        return goyaCacheManager;
    }
}
