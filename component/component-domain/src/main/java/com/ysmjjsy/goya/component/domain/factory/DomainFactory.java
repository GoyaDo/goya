package com.ysmjjsy.goya.component.domain.factory;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHelper;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:30
 */
public class DomainFactory {

    public static <T> T create(Class<T> entityClz){
        return ApplicationContextHelper.getBean(entityClz);
    }

}
