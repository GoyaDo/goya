package com.ysmjjsy.goya.module.domain.command;

import com.ysmjjsy.goya.component.common.pojo.domain.Command;
import com.ysmjjsy.goya.module.domain.event.DomainEventQueue;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 15:06
 */
@FunctionalInterface
public interface CommandHandler {

    /**
     * handle
     * @param queue 事件队列
     * @param command command
     */
    void handle(DomainEventQueue queue, Command command);
}
