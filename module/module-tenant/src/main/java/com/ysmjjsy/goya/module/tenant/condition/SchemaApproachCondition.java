package com.ysmjjsy.goya.module.tenant.condition;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.db.constants.DataConstants;
import com.ysmjjsy.goya.module.tenant.enums.MultiTenantApproach;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>Description: 标准算法策略条件 </p>
 *
 * @author goya
 * @since 2022/5/3 23:00
 */
public class SchemaApproachCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(SchemaApproachCondition.class);

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String property = PropertyResolver.getProperty(conditionContext, DataConstants.ITEM_MULTI_TENANT_APPROACH);
        boolean result = StringUtils.isNotBlank(property) && StringUtils.equalsIgnoreCase(property, MultiTenantApproach.SCHEMA.name());
        log.debug("[Goya] |- Condition [Schema Approach] value is [{}]", result);
        return result;
    }
}
