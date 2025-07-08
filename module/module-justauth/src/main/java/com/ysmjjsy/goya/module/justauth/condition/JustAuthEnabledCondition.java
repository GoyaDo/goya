package com.ysmjjsy.goya.module.justauth.condition;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.module.justauth.constants.JustAuthConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: JusAuth注入条件 </p>
 *
 * @author goya
 * @since 2021/5/27 22:08
 */
public class JustAuthEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(JustAuthEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, JustAuthConstants.ITEM_JUSTAUTH_ENABLED, false);
        log.debug("[Goya] |- Condition [JustAuth Enabled] value is [{}]", result);
        return result;
    }
}
