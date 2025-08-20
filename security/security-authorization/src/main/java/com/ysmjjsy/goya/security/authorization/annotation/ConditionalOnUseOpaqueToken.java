package com.ysmjjsy.goya.security.authorization.annotation;

import com.ysmjjsy.goya.security.authorization.condition.UseOpaqueTokenCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: 使用 Opaque Token 条件注解 </p>
 *
 * @author goya
 * @since 2024/4/8 16:27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(UseOpaqueTokenCondition.class)
public @interface ConditionalOnUseOpaqueToken {
}
