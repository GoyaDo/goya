package com.ysmjjsy.goya.component.cache.configuration.properties;

import com.alicp.jetcache.anno.CacheType;
import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 16:18
 */
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_PREFIX_CACHE)
public record CacheProperties(

         /*
          指定缓存区域，与 JetCache Area 配置对应。
         */
        String area,
        /*
          缓存方式。默认：多级缓存
         */
        CacheType cacheType,
        /*
          缓存过期时间，默认两小时
          <p>
          使用Duration类型，配置参数形式如下：
          "?ns" //纳秒
          "?us" //微秒
          "?ms" //毫秒
          "?s" //秒
          "?m" //分
          "?h" //小时
          "?d" //天
         */
        Duration expire,
        /*
          是否允许存储空值
         */
        Boolean allowNullValues,
        /*
          是否开启多实例本地缓存同步，默认：不开启
          仅在多级缓存模式下生效。
         */
        Boolean sync,
        /*
          本地缓存过期时间
         */
        Duration localExpire,
        /*
          本地缓存数量限制。
         */
        Integer localLimit,
        /*
          是否开启缓存穿透保护, 默认：true
         */
        Boolean penetrationProtect,
        /*
          缓存穿透保护有效期，默认：2 小时。
         */
        Duration penetrationProtectTimeout
) {

    public CacheProperties {
        if (cacheType == null) {
            cacheType = CacheType.BOTH;
        }
        if (expire == null) {
            expire = Duration.ofHours(2);
        }
        if (allowNullValues == null) {
            allowNullValues = true;
        }
        if (sync == null) {
            sync = false;
        }
    }
}
