package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * ExternalTransitionBuilder
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
public interface ExternalTransitionBuilder<S, E, C> {
    /**
     * Build transition source state.
     * @param stateId id of state
     * @return from clause builder
     */
    From<S, E, C> from(S stateId);

}
