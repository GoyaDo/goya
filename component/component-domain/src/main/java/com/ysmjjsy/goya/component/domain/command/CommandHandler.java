package com.ysmjjsy.goya.component.domain.command;

import com.ysmjjsy.goya.component.domain.event.DomainEventQueue;
import com.ysmjjsy.goya.component.pojo.domain.Command;

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
