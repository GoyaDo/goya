package com.ysmjjsy.goya.component.core.init;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * <p>应用初始化事件</p>
 * 通过此事件可以查看业务系统所有初始化行为
 *
 * @author goya
 * @since 2025/7/24 23:42
 */
public class ApplicationInitializingEvent extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 6633683677899182977L;

    public ApplicationInitializingEvent(Object source) {
        super(source);
    }
}
