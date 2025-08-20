package com.ysmjjsy.goya.component.catchlog.handler;


import com.ysmjjsy.goya.component.core.context.SpringContextHolder;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
public class ResponseHandlerFactory {

    public static ResponseHandlerI get(){
        if(SpringContextHolder.getBean(ResponseHandlerI.class) != null){
            return SpringContextHolder.getBean(ResponseHandlerI.class);
        }
        return new DefaultResponseHandler();
    }
}
