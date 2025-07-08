package com.ysmjjsy.goya.component.web.stamp;

import com.ysmjjsy.goya.component.cache.jetcache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.component.web.constants.WebConstants;
import com.ysmjjsy.goya.component.web.properties.SecureProperties;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * <p>Description: 防刷签章管理器 </p>
 * <p>
 * 这里使用Long类型作为值的存储类型，是为了解决该Cache 同时可以存储Duration相关的数据
 *
 * @author goya
 * @since 2021/8/25 21:43
 */
@Getter
public class AccessLimitedStampManager extends AbstractStampManager<String, Long> {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedStampManager.class);

    private final SecureProperties secureProperties;

    public AccessLimitedStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_ACCESS_LIMITED);
        this.secureProperties = secureProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(secureProperties.getAccessLimited().getExpire());
    }

    /**
     * 计算剩余过期时间
     * <p>
     * 每次create或者put，缓存的过期时间都会被覆盖。（注意：Jetcache put 方法的参数名：expireAfterWrite）。
     * 因为Jetcache没有Redis的incr之类的方法，那么每次放入Times值，都会更新过期时间，实际操作下来是变相的延长了过期时间。
     *
     * @param configuredDuration 注解上配置的、且可以正常解析的Duration值
     * @param expireKey          时间标记存储Key值。
     * @return 还剩余的过期时间 {@link Duration}
     */
    public Duration calculateRemainingTime(Duration configuredDuration, String expireKey) {
        Long begin = get(expireKey);
        Long current = System.currentTimeMillis();
        long interval = current - begin;

        log.debug("[Goya] |- AccessLimited operation interval [{}] millis.", interval);

        Duration duration;
        if (!configuredDuration.isZero()) {
            duration = configuredDuration.minusMillis(interval);
        } else {
            duration = getExpire().minusMillis(interval);
        }

        return duration;
    }
}
