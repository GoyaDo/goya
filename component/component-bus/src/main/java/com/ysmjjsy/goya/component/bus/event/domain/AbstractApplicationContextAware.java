package com.ysmjjsy.goya.component.bus.event.domain;

import com.ysmjjsy.goya.component.bus.api.GoyaBus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>Description: 抽象 JPA 实体变更 Listener</p>
 *
 * @author goya
 * @since 2021/8/11 18:12
 */
@RequiredArgsConstructor
public abstract class AbstractApplicationContextAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void publishEvent(GoyaEvent event) {
        GoyaBus goyaBus = applicationContext.getBean(GoyaBus.class);
        goyaBus.publish(event);
    }
}
