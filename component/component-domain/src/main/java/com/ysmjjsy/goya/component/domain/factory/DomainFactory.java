package com.ysmjjsy.goya.component.domain.factory;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:30
 */
public class DomainFactory {

    public static <T> T create(Class<T> entityClz){
        return ApplicationContextHolder.getBean(entityClz);
    }

}
