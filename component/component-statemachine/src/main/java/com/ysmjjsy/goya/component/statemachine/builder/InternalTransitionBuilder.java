package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * InternalTransitionBuilder
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface InternalTransitionBuilder <S, E, C> {
    /**
     * Build a internal transition
     * @param stateId id of transition
     * @return To clause builder
     */
    To<S, E, C> within(S stateId);
}
