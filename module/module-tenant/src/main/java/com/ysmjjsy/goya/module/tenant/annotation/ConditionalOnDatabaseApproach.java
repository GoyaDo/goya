package com.ysmjjsy.goya.module.tenant.annotation;

import com.ysmjjsy.goya.module.tenant.condition.DatabaseApproachCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: Database 模式多租户条件注解 </p>
 *
 * @author goya
 * @since 2022/5/3 23:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(DatabaseApproachCondition.class)
public @interface ConditionalOnDatabaseApproach {
}
