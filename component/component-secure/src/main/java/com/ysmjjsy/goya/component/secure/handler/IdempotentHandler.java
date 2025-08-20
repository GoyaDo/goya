package com.ysmjjsy.goya.component.secure.handler;

import com.ysmjjsy.goya.component.common.exception.request.RepeatSubmissionException;
import com.ysmjjsy.goya.component.secure.annotation.Idempotent;
import com.ysmjjsy.goya.component.secure.stamp.IdempotentStampManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * <p>Description: 幂等处理器 </p>
 *
 * @author goya
 * @since 2024/4/30 19:41
 */
public class IdempotentHandler {

    private static final Logger log = LoggerFactory.getLogger(IdempotentHandler.class);

    public static boolean handle(String key, Idempotent idempotent, String url, IdempotentStampManager idempotentStampManager) {
        // 幂等性校验, 根据缓存中是否存在Token进行校验。
        // 如果缓存中没有Token，通过放行, 同时在缓存中存入Token。
        // 如果缓存中有Token，意味着同一个操作反复操作，认为失败则抛出异常, 并通过统一异常处理返回友好提示
        if (StringUtils.isNotBlank(key)) {
            String token = idempotentStampManager.get(key);
            if (StringUtils.isBlank(token)) {
                Duration configuredDuration = Duration.ZERO;
                String annotationExpire = idempotent.expire();
                if (StringUtils.isNotBlank(annotationExpire)) {
                    try {
                        configuredDuration = Duration.parse(annotationExpire);
                    } catch (DateTimeParseException e) {
                        log.warn("[Goya] |- Idempotent duration value is incorrect, on api [{}].", url);
                    }
                }

                if (!configuredDuration.isZero()) {
                    idempotentStampManager.create(key, configuredDuration);
                } else {
                    idempotentStampManager.create(key);
                }

                return true;
            } else {
                throw new RepeatSubmissionException("Don't Repeat Submission");
            }
        }
        return true;
    }
}
