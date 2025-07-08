package com.ysmjjsy.goya.module.sms.annotation;

import com.ysmjjsy.goya.module.sms.condition.SmsEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: 短信开启条件注解 </p>
 *
 * @author goya
 * @since 2022/1/26 15:34
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(SmsEnabledCondition.class)
public @interface ConditionalOnSmsEnabled {
}
