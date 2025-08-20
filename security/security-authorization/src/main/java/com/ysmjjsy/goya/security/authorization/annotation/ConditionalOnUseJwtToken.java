package com.ysmjjsy.goya.security.authorization.annotation;

import com.ysmjjsy.goya.security.authorization.condition.UseJwtTokenCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: 使用 Jwt Token 条件注解 </p>
 *
 * @author goya
 * @since 2024/4/8 16:27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(UseJwtTokenCondition.class)
public @interface ConditionalOnUseJwtToken {
}
