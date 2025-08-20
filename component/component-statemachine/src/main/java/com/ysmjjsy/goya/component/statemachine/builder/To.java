package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * To
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface To<S, E, C> {
    /**
     * Build transition event
     * @param event transition event
     * @return On clause builder
     */
    On<S, E, C> on(E event);
}
