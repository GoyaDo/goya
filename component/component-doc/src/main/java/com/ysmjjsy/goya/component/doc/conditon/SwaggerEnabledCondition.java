package com.ysmjjsy.goya.component.doc.conditon;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.core.resolver.PropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: Swagger 开启条件 </p>
 *
 * @author goya
 * @since 2025/6/13 18:00
 */
public class SwaggerEnabledCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(SwaggerEnabledCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata metadata) {
        boolean result = PropertyResolver.getBoolean(conditionContext, GoyaConstants.ITEM_DOC_ENABLED);
        log.debug("[Goya] |- Condition [Swagger Enabled] value is [{}]", result);
        return result;
    }
}
