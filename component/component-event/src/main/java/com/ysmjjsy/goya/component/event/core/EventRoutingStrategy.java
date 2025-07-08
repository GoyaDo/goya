package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import com.ysmjjsy.goya.component.dto.enums.Architecture;

/**
 * 事件路由策略枚举
 * 定义事件的处理方式：本地、远程或混合
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public enum EventRoutingStrategy {

    /**
     * 仅本地处理，不进行远程广播
     */
    LOCAL_ONLY,

    /**
     * 仅远程广播，不进行本地处理
     */
    REMOTE_ONLY,

    /**
     * 先本地处理，再远程广播
     */
    LOCAL_AND_REMOTE,

    /**
     * 先远程广播，再本地处理
     */
    REMOTE_FIRST;


    public static EventRoutingStrategy getSystemDefault() {
        Architecture architecture = GoyaContextHolder.getInstance().getArchitecture();
        if (Architecture.MONOCOQUE.equals(architecture)) {
            return LOCAL_ONLY;
        } else {
            return REMOTE_ONLY;
        }
    }
}