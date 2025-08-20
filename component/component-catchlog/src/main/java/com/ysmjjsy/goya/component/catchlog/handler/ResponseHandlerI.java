package com.ysmjjsy.goya.component.catchlog.handler;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:37
 */
public interface ResponseHandlerI {
    Object handle(Class returnType, String errCode, String errMsg);
}
