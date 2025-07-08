package com.ysmjjsy.goya.component.catchlog.handler;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHelper;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
public class ResponseHandlerFactory {

    public static ResponseHandlerI get(){
        if(ApplicationContextHelper.getBean(ResponseHandlerI.class) != null){
            return ApplicationContextHelper.getBean(ResponseHandlerI.class);
        }
        return new DefaultResponseHandler();
    }
}
