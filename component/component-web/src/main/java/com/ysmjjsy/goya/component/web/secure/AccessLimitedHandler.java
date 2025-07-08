package com.ysmjjsy.goya.component.web.secure;

import com.ysmjjsy.goya.component.exception.request.FrequentRequestsException;
import com.ysmjjsy.goya.component.web.annotation.AccessLimited;
import com.ysmjjsy.goya.component.web.stamp.AccessLimitedStampManager;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * <p>Description: 访问限制处理器 </p>
 *
 * @author goya
 * @since 2024/4/30 19:41
 */
public class AccessLimitedHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitedHandler.class);

    public static boolean handle(String key, AccessLimited accessLimited, String url, AccessLimitedStampManager accessLimitedStampManager) {

        if (StringUtils.isNotBlank(key)) {

            int maxTimes = accessLimitedStampManager.getSecureProperties().getAccessLimited().getMaxTimes();
            Duration expireDuration = Duration.ZERO;

            int annotationMaxTimes = accessLimited.maxTimes();
            if (annotationMaxTimes != 0) {
                maxTimes = annotationMaxTimes;
            }

            String annotationDuration = accessLimited.duration();
            if (StringUtils.isNotBlank(annotationDuration)) {
                try {
                    expireDuration = Duration.parse(annotationDuration);
                } catch (DateTimeParseException e) {
                    log.warn("[Goya] |- AccessLimited duration value is incorrect, on api [{}].", url);
                }
            }

            String expireKey = key + "_expire";
            Long times = accessLimitedStampManager.get(key);

            if (ObjectUtils.isEmpty(times) || times == 0L) {
                if (!expireDuration.isZero()) {
                    // 如果注解上配置了Duration且没有配置错可以正常解析，那么使用注解上的配置值
                    accessLimitedStampManager.create(key, expireDuration);
                    accessLimitedStampManager.put(expireKey, System.currentTimeMillis(), expireDuration);
                } else {
                    // 如果注解上没有配置Duration或者配置错无法正常解析，那么使用StampProperties的配置值
                    accessLimitedStampManager.create(key);
                    accessLimitedStampManager.put(expireKey, System.currentTimeMillis());
                }
                return true;
            } else {
                log.debug("[Goya] |- AccessLimited request [{}] times.", times);

                if (times <= maxTimes) {
                    Duration newDuration = accessLimitedStampManager.calculateRemainingTime(expireDuration, expireKey);
                    // 不管是注解上配置Duration值还是StampProperties中配置的Duration值，是不会变的
                    // 所以第一次存入expireKey对应的System.currentTimeMillis()时间后，这个值也不应该变化。
                    // 因此，这里只更新访问次数的标记值
                    accessLimitedStampManager.put(key, times + 1L, newDuration);
                    return true;
                } else {
                    throw new FrequentRequestsException("Requests are too frequent. Please try again later!");
                }
            }
        }

        return true;
    }
}
