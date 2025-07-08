package com.ysmjjsy.goya.component.domain.annotation;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <p>实体，实体对象是原型，不是线程安全的</p>
 *
 * @author goya
 * @since 2025/6/13 23:28
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface DomainEntity {
}
