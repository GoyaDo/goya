package com.ysmjjsy.goya.module.redis.constants;

import static com.ysmjjsy.goya.component.dto.constants.GoyaConstants.PROPERTY_PREFIX_CACHE;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 10:40
 */
public interface RedisConstants {

    String PROPERTY_PREFIX_REDIS = PROPERTY_PREFIX_CACHE + ".redis";
    String PROPERTY_PREFIX_REDISSON = PROPERTY_PREFIX_REDIS + ".redisson";
}
