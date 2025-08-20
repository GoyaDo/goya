package com.ysmjjsy.goya.component.statemachine.builder;

/**
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
public interface ExternalParallelTransitionBuilder<S, E, C> {
    /**
     * Build transition source state.
     * @param stateId id of state
     * @return from clause builder
     */
    ParallelFrom<S, E, C> from(S stateId);

}
