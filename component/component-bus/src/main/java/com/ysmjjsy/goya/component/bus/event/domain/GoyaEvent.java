package com.ysmjjsy.goya.component.bus.event.domain;

import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/31 16:18
 */
public interface GoyaEvent extends Serializable {

    /**
     * 事件类型
     * @return 事件类型
     */
    EventType eventType();

    /**
     * 获取事件的全局唯一标识符
     * 用于消息追踪、幂等去重等场景
     *
     * @return 事件ID，应当全局唯一且稳定
     */
    String getEventId();

    /**
     * 获取事件key
     * 用于消息路由、监听器匹配等场景
     *
     * @return 事件类型，如 "order.created", "payment.failed"
     */
    String getKey();

    /**
     * 获取事件创建时间
     *
     * @return 事件创建的时间戳
     */
    LocalDateTime getCreateTime();

    /**
     * 获取事件状态
     * @return 事件状态
     */
    EventStatus getEventStatus();

    /**
     * 设置事件状态
     * @param eventStatus 事件状态
     */
    void setEventStatus(EventStatus eventStatus);
}
