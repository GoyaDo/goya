package com.ysmjjsy.goya.module.wechat.annotation;

import com.ysmjjsy.goya.module.wechat.condition.WxappEnabledCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * <p>Description: 微信小程序开启条件注解 </p>
 *
 * @author goya
 * @since 2022/1/24 14:40
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(WxappEnabledCondition.class)
public @interface ConditionalOnWxAppEnabled {
}
