package com.ysmjjsy.goya.component.domain.command;

import com.ysmjjsy.goya.component.domain.event.DomainEventQueue;

import java.util.function.Function;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 14:18
 */
@FunctionalInterface
public interface CommandInvoker {

    <R> R invoke(Function<DomainEventQueue, R> block);

}
