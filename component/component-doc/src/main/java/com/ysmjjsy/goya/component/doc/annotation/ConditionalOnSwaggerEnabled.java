package com.ysmjjsy.goya.component.doc.annotation;

import com.ysmjjsy.goya.component.doc.conditon.SwaggerEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: Swagger条件开启主机 </p>
 *
 * @author goya
 * @since 2025/6/13 18:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(SwaggerEnabledCondition.class)
public @interface ConditionalOnSwaggerEnabled {
}
