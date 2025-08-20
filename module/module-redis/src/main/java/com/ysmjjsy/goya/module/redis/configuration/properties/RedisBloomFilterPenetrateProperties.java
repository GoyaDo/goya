package com.ysmjjsy.goya.module.redis.configuration.properties;

import com.ysmjjsy.goya.module.redis.constants.RedisConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/29 23:42
 */
@Data
@ConfigurationProperties(prefix = RedisConstants.REDIS_BLOOM_FILTER_DEFAULT_PREFIX)
public class RedisBloomFilterPenetrateProperties {

    private boolean enabled = true;

    /**
     * 布隆过滤器默认实例名称
     */
    private String name = "cache_penetration_bloom_filter";

    /**
     * 每个元素的预期插入量
     */
    private Long expectedInsertions = 64000L;

    /**
     * 预期错误概率
     */
    private Double falseProbability = 0.03D;
}
