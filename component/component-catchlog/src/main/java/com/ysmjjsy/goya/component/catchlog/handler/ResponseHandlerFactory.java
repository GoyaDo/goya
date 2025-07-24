package com.ysmjjsy.goya.component.catchlog.handler;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
public class ResponseHandlerFactory {

    public static ResponseHandlerI get(){
        if(ApplicationContextHolder.getBean(ResponseHandlerI.class) != null){
            return ApplicationContextHolder.getBean(ResponseHandlerI.class);
        }
        return new DefaultResponseHandler();
    }
}
