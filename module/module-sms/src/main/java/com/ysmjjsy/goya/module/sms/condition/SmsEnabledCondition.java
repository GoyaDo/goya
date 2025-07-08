package com.ysmjjsy.goya.module.sms.condition;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.module.sms.constants.SmsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 短信开启条件 </p>
 *
 * @author goya
 * @since 2022/1/26 15:33
 */
public class SmsEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(SmsEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, SmsConstants.ITEM_SMS_ENABLED);
        log.debug("[Goya] |- Condition [Sms Enabled] value is [{}]", result);
        return result;
    }
}
