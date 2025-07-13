package com.ysmjjsy.goya.component.bus.message.domain;

import java.io.Serializable;

/**
 * <p>Description: 统一消息定义 </p>
 *
 * @author goya
 * @since 2023/10/26 12:16
 */
public interface Message<T> extends Serializable {

    /**
     * 消息内容
     *
     * @return 消息内容
     */
    T getPayload();
}
