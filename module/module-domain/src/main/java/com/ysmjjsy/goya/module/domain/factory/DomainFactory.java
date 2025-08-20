package com.ysmjjsy.goya.module.domain.factory;


import com.ysmjjsy.goya.component.core.context.SpringContextHolder;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:30
 */
public class DomainFactory {

    public static <T> T create(Class<T> entityClz){
        return SpringContextHolder.getBean(entityClz);
    }

}
