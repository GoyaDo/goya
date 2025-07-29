package com.ysmjjsy.goya.security.authorization.condition;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.pojo.enums.Target;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 使用 JWT Token 条件 </p>
 *
 * @author goya
 * @since 2024/4/8 16:01
 */
public class UseJwtTokenCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(UseJwtTokenCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = PropertyResolver.getProperty(conditionContext, SecurityConstants.ITEM_AUTHORIZATION_VALIDATE);
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, Target.LOCAL.name());
        log.debug("[Goya] |- Condition [Use JWT Token] value is [{}]", result);
        return result;
    }
}
