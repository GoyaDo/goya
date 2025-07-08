package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>Description: 抽象 JPA 实体变更 Listener</p>
 *
 * @author goya
 * @since 2021/8/11 18:12
 */
public abstract class AbstractApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private GoyaEventBus goyaEventBus;

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

    protected void publishEvent(GoyaEvent event) {
        goyaEventBus.publish(event);
    }
}
