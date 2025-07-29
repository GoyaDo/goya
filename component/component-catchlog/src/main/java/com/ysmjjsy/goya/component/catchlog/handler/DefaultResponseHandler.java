package com.ysmjjsy.goya.component.catchlog.handler;

import com.ysmjjsy.goya.component.pojo.response.Response;
import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
@Slf4j
public class DefaultResponseHandler implements ResponseHandlerI{

    @Override
    public  Object handle(Class returnType, String errCode, String errMsg){
        if (isColaResponse(returnType)){
            return handleColaResponse(errCode, errMsg);
        }
        return null;
    }

    public  Object handle(Class returnType, GoyaRuntimeException e){
        return handle(returnType, e.getErrorCode().getCode(), e.getMessage());
    }


    private static Object handleColaResponse(String errCode, String errMsg) {
        try {
            return Response.failure(errCode, errMsg);
        }
        catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return  null;
        }
    }

    private static boolean isColaResponse(Class returnType) {
        return  returnType == Response.class || returnType.getGenericSuperclass() == Response.class;
    }
}
