package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * StateMachineBuilder
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface ParallelFrom<S, E, C> {
    /**
     * Build transition target state and return to clause builder
     * @param stateIds id of state
     * @return To clause builder
     */
    To<S, E, C> toAmong(S ... stateIds);

}
