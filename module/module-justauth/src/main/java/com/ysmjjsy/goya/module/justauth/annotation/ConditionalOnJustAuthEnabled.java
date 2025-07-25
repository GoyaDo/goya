package com.ysmjjsy.goya.module.justauth.annotation;

import com.ysmjjsy.goya.module.justauth.condition.JustAuthEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: JustAuth开启条件注解 </p>
 *
 * @author goya
 * @since 2022/1/24 14:40
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(JustAuthEnabledCondition.class)
public @interface ConditionalOnJustAuthEnabled {
}
