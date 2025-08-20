package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * From
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface From<S, E, C> {
    /**
     * Build transition target state and return to clause builder
     * @param stateId id of state
     * @return To clause builder
     */
    To<S, E, C> to(S stateId);

}
