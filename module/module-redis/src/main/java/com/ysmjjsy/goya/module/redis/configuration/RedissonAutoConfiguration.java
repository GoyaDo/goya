package com.ysmjjsy.goya.module.redis.configuration;

import com.ysmjjsy.goya.component.core.resolver.ResourceResolver;
import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.common.constants.SymbolConstants;
import com.ysmjjsy.goya.module.redis.configuration.properties.RedisBloomFilterPenetrateProperties;
import com.ysmjjsy.goya.module.redis.configuration.properties.RedissonProperties;
import com.ysmjjsy.goya.module.redis.constants.RedisConstants;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.ysmjjsy.goya.module.redis.constants.RedisConstants.PROPERTY_PREFIX_REDISSON;

/**
 * <p>Description: Redisson配置 </p>
 *
 * @author goya
 * @since 2021/10/22 13:56
 */
@AutoConfiguration
@ConditionalOnProperty(value = PROPERTY_PREFIX_REDISSON + GoyaConstants.PROPERTY_ENABLED, havingValue = "true")
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    private final RedissonProperties redissonProperties;
    private final RedisProperties redisProperties;

    public RedissonAutoConfiguration(RedissonProperties redissonProperties, RedisProperties redisProperties) {
        this.redissonProperties = redissonProperties;
        this.redisProperties = redisProperties;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [redis] RedissonAutoConfiguration auto configure.");
    }

    /**
     * 防止缓存穿透的布隆过滤器
     */
    @Bean
    @ConditionalOnProperty(prefix = RedisConstants.REDIS_BLOOM_FILTER_DEFAULT_PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
    public RBloomFilter<String> cachePenetrationBloomFilter(RedissonClient redissonClient, RedisBloomFilterPenetrateProperties bloomFilterPenetrateProperties) {
        RBloomFilter<String> cachePenetrationBloomFilter = redissonClient.getBloomFilter(bloomFilterPenetrateProperties.getName());
        cachePenetrationBloomFilter.tryInit(bloomFilterPenetrateProperties.getExpectedInsertions(), bloomFilterPenetrateProperties.getFalseProbability());
        log.trace("[Goya] |- module [redis] |- bean [cachePenetrationBloomFilter] register.");
        return cachePenetrationBloomFilter;
    }

    private File readConfigFile() {
        String configFile = redissonProperties.getConfig();
        if (StringUtils.isNotBlank(configFile)) {
            try {
                return ResourceResolver.getFile(configFile);
            } catch (IOException e) {
                log.error("[Goya] |- Can not found the config file [{}], check whether the format is correct.", configFile);
            }
        }

        return null;
    }

    private Config getConfigByFile() {
        try {
            File configFile = readConfigFile();
            if (ObjectUtils.isNotEmpty(configFile)) {
                if (StringUtils.endsWithIgnoreCase(configFile.getName(), SymbolConstants.SUFFIX_YAML)) {
                    return Config.fromYAML(configFile);
                }
            }
        } catch (IOException e) {
            log.error("[Goya] |- Redisson loading the config file error!");
        }

        return null;
    }

    private Config getDefaultConfig() {
        Config config = new Config();

        switch (redissonProperties.getMode()) {
            case CLUSTER -> {
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                // 未配置redisson的cluster配置时，使用 spring.data.redis 的配置
                ClusterServersConfig redissonClusterConfig = redissonProperties.getClusterServersConfig();
                if (ObjectUtils.isNotEmpty(redissonClusterConfig)) {
                    BeanUtils.copyProperties(redissonClusterConfig, clusterServersConfig, ClusterServersConfig.class);
                    clusterServersConfig.setNodeAddresses(redissonClusterConfig.getNodeAddresses());
                }
                if (CollectionUtils.isEmpty(clusterServersConfig.getNodeAddresses())) {
                    // 使用 spring.data.redis 的
                    List<String> nodes = redisProperties.getCluster().getNodes();
                    nodes.stream().map(a -> redissonProperties.getProtocol() + a).forEach(clusterServersConfig::addNodeAddress);
                }
                if (StringUtils.isBlank(clusterServersConfig.getPassword()) && StringUtils.isNotBlank(redisProperties.getPassword())) {
                    // 使用 spring.data.redis 的
                    clusterServersConfig.setPassword(redisProperties.getPassword());
                }
            }
            case SENTINEL -> {
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                SentinelServersConfig redissonSentinelConfig = redissonProperties.getSentinelServersConfig();
                if (ObjectUtils.isNotEmpty(redissonSentinelConfig)) {
                    BeanUtils.copyProperties(redissonSentinelConfig, sentinelServersConfig, SentinelServersConfig.class);
                    sentinelServersConfig.setSentinelAddresses(redissonSentinelConfig.getSentinelAddresses());
                }
                if (CollectionUtils.isEmpty(sentinelServersConfig.getSentinelAddresses())) {
                    // 使用 spring.data.redis 的配置
                    List<String> nodes = redisProperties.getSentinel().getNodes();
                    nodes.stream().map(a -> redissonProperties.getProtocol() + a).forEach(sentinelServersConfig::addSentinelAddress);
                }
                if (StringUtils.isBlank(sentinelServersConfig.getPassword()) && StringUtils.isNotBlank(redisProperties.getPassword())) {
                    // 使用 spring.data.redis 的配置
                    sentinelServersConfig.setPassword(redisProperties.getPassword());
                }
                if (StringUtils.isBlank(sentinelServersConfig.getMasterName())) {
                    // 使用 spring.data.redis 的配置
                    sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
                }
                // database 不做处理，以免不生效
            }
            default -> {
                SingleServerConfig singleServerConfig = config.useSingleServer();
                if (ObjectUtils.isNotEmpty(redissonProperties.getSingleServerConfig())) {
                    BeanUtils.copyProperties(redissonProperties.getSingleServerConfig(), singleServerConfig, SingleServerConfig.class);
                }
                if (StringUtils.isBlank(singleServerConfig.getAddress())) {
                    // 使用 spring.data.redis 的配置
                    singleServerConfig.setAddress(redissonProperties.getProtocol() + redisProperties.getHost() + SymbolConstants.COLON + redisProperties.getPort());
                }
                if (StringUtils.isBlank(singleServerConfig.getPassword()) && StringUtils.isNotBlank(redisProperties.getPassword())) {
                    // 使用 spring.data.redis 的配置
                    singleServerConfig.setPassword(redisProperties.getPassword());
                }
                // 单机模式下，database使用redis的
                singleServerConfig.setDatabase(redisProperties.getDatabase());
            }
        }

        config.setCodec(new JsonJacksonCodec());
        //默认情况下，看门狗的检查锁的超时时间是30秒钟
        config.setLockWatchdogTimeout(1000 * 30);
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = getConfigByFile();
        if (ObjectUtils.isEmpty(config)) {
            config = getDefaultConfig();
        }

        RedissonClient redissonClient = Redisson.create(config);

        log.trace("[Goya] |- module [redis] |- bean [redissonClient] register.");

        return redissonClient;
    }
}
