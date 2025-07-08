package com.ysmjjsy.goya.component.event.annotation;

import com.ysmjjsy.goya.component.event.core.EventRoutingStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 统一事件监听器注解
 * 标记方法为事件监听器
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GoyaAnnoEventListener {

    /**
     * 监听的事件类型
     * 如果不指定，将从方法参数中推断
     */
    Class<?>[] value() default {};

    /**
     * 别名，同 value
     */
    @AliasFor("value")
    Class<?>[] events() default {};

    /**
     * 是否异步处理
     */
    boolean async() default false;

    /**
     * 监听器优先级，数字越小优先级越高
     */
    int priority() default 0;

    /**
     * 条件表达式，支持 SpEL
     * 只有当条件为 true 时才处理事件
     */
    String condition() default "";

    /**
     * 监听的主题/队列名称
     * 用于远程事件订阅
     */
    String[] topics() default {};

    /**
     * 事件路由策略
     */
    EventRoutingStrategy routingStrategy() default EventRoutingStrategy.LOCAL_AND_REMOTE;

    /**
     * 是否是事务事件监听器
     * 如果为 true，将使用 @TransactionalEventListener
     */
    boolean transactional() default false;

    /**
     * 事务阶段（仅当 transactional=true 时生效）
     */
    TransactionPhase phase() default TransactionPhase.AFTER_COMMIT;

    /**
     * 事务阶段枚举
     */
    enum TransactionPhase {
        BEFORE_COMMIT,
        AFTER_COMMIT,
        AFTER_ROLLBACK,
        AFTER_COMPLETION
    }
}