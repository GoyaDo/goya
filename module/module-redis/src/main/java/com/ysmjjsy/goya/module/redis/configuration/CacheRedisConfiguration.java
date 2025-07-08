package com.ysmjjsy.goya.module.redis.configuration;

import com.ysmjjsy.goya.component.cache.properties.CacheProperties;
import com.ysmjjsy.goya.component.event.serializer.EventSerializer;
import com.ysmjjsy.goya.module.redis.enhance.GoyaRedisCacheManager;
import com.ysmjjsy.goya.module.redis.event.RedisEventTransport;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
@EnableConfigurationProperties(CacheProperties.class)
public class CacheRedisConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheRedisConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- Module [Cache Redis] Configure.");
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
        log.trace("[Goya] |- Bean [Jackson2Json Redis Serializer] Configure.");
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

        log.trace("[Goya] |- Bean [Redis Template] Configure.");

        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        log.trace("[Goya] |- Bean [String Redis Template] Configure.");
        return stringRedisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        log.trace("[Goya] |- Bean [Redis Message Listener Container] Configure.");
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
        log.trace("[Goya] |- Bean [Redis Cache Manager] Configure.");
        return goyaRedisCacheManager;
    }

    @Configuration(proxyBeanMethods = false)
    @ComponentScan({
            "com.ysmjjsy.goya.module.redis.utils"
    })
    static class RedisUtilsConfiguration {

    }

    /**
     * Redis 传输配置
     */
    @Configuration
    @ConditionalOnClass({RedisTemplate.class, RedisMessageListenerContainer.class})
    @ConditionalOnProperty(prefix = "goya.event", name = "enabled", havingValue = "true", matchIfMissing = true)
    static class RedisTransportConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public RedisMessageListenerContainer redisMessageListenerContainer() {
            RedisMessageListenerContainer container = new RedisMessageListenerContainer();
            // 配置将在 RedisEventTransport 中完成
            return container;
        }

        @Bean
        public RedisEventTransport redisEventTransport(RedisTemplate<String, String> redisTemplate,
                                                       EventSerializer eventSerializer,
                                                       @Qualifier("redisMessageListenerContainer")  RedisMessageListenerContainer messageListenerContainer) {
            log.info("Creating Redis event transport");
            RedisEventTransport transport = new RedisEventTransport(redisTemplate, eventSerializer, messageListenerContainer);
            transport.start();
            return transport;
        }
    }
}
