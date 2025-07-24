package com.ysmjjsy.goya.component.bus.event.domain;

import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * <p>Description: 抽象 JPA 实体变更 Listener</p>
 *
 * @author goya
 * @since 2021/8/11 18:12
 */
@RequiredArgsConstructor
public abstract class AbstractApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    protected ApplicationContext getApplicationContext() {
        if (ObjectUtils.isEmpty(applicationContext)) {
            return GoyaContextHolder.getInstance().getApplicationContext();
        }
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(ApplicationEvent event) {
        GoyaContextHolder.getInstance().publishEvent(event);
    }
}
