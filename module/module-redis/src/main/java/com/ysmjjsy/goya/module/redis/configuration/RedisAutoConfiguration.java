package com.ysmjjsy.goya.module.redis.configuration;

import com.ysmjjsy.goya.component.cache.configuration.properties.CacheProperties;
import com.ysmjjsy.goya.module.redis.configuration.properties.RedisBloomFilterPenetrateProperties;
import com.ysmjjsy.goya.module.redis.definition.DistributedCacheTemplateProxy;
import com.ysmjjsy.goya.module.redis.definition.GoyaRedisCache;
import com.ysmjjsy.goya.module.redis.distributedid.LocalRedisWorkIdChoose;
import com.ysmjjsy.goya.module.redis.enhance.GoyaRedisCacheManager;
import jakarta.annotation.PostConstruct;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>Description: Redis 配置 </p>
 *
 * @author goya
 * @since 2022/5/23 17:00
 */
@AutoConfiguration
@EnableConfigurationProperties({CacheProperties.class, RedisBloomFilterPenetrateProperties.class})
public class RedisAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [redis] RedisAutoConfiguration auto configure.");
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    /**
     * 命名为 springSessionDefaultRedisSerializer 是因为 Spring Session 会用到。
     * {@link org.springframework.session.data.redis.config.annotation.web.http.AbstractRedisHttpSessionConfiguration}
     * {@link org.springframework.session.data.redis.config.annotation.web.server.RedisWebSessionConfiguration}
     *
     * @return {@link RedisSerializer}
     */
//    @Bean(name = "springSessionDefaultRedisSerializer")
    private RedisSerializer<Object> valueSerializer() {
        RedisSerializer<Object> redisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        log.trace("[Goya] |- module [redis] |- bean [valueSerializer] register.");
        return redisSerializer;
    }

    /**
     * 重新配置一个RedisTemplate
     *
     * @return {@link RedisTemplate}
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();

        log.trace("[Goya] |- module [redis] |- bean [redisTemplate] register.");

        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        log.trace("[Goya] |- module [redis] |- bean [stringRedisTemplate] register.");
        return stringRedisTemplate;
    }

    @Bean
    public GoyaRedisCache distributedCache(StringRedisTemplate stringRedisTemplate, CacheProperties cacheProperties, RedissonClient redissonClient){
        DistributedCacheTemplateProxy distributedCacheTemplateProxy = new DistributedCacheTemplateProxy(stringRedisTemplate, cacheProperties, redissonClient);
        log.trace("[Goya] |- module [redis] |- bean [distributedCache] register.");
        return distributedCacheTemplateProxy;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        log.trace("[Goya] |- module [redis] |- bean [redisMessageListenerContainer] register.");
        return container;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);

        // 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration 对象，所以要注意对象的引用关系。
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(cacheProperties.expire());

        boolean allowNullValues = cacheProperties.allowNullValues();
        if (!allowNullValues) {
            // 注意：这里 RedisCacheConfiguration 每一个方法调用之后，都会返回一个新的 RedisCacheConfiguration 对象，所以要注意对象的引用关系。
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }

        GoyaRedisCacheManager goyaRedisCacheManager = new GoyaRedisCacheManager(redisCacheWriter, redisCacheConfiguration, cacheProperties);
        goyaRedisCacheManager.setTransactionAware(false);
        goyaRedisCacheManager.afterPropertiesSet();
        log.trace("[Goya] |- module [redis] |- bean [redisCacheManager] register.");
        return goyaRedisCacheManager;
    }

    /**
     * 本地 Redis 构建雪花 WorkId 选择器
     */
    @Bean(name = "snowflakeWorkIdChoose")
    public LocalRedisWorkIdChoose snowflakeWorkIdChoose(StringRedisTemplate stringRedisTemplate) {
        LocalRedisWorkIdChoose localRedisWorkIdChoose = new LocalRedisWorkIdChoose(stringRedisTemplate);
        log.trace("[Goya] |- module [redis] |- bean [snowflakeWorkIdChoose] register.");
        return localRedisWorkIdChoose;
    }

}
