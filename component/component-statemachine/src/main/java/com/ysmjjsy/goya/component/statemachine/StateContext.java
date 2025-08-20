package com.ysmjjsy.goya.component.statemachine;

/**
 * StateContext
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface StateContext<S, E, C> {
    /**
     * Gets the transition.
     *
     * @return the transition
     */
    Transition<S, E, C> getTransition();

    /**
     * Gets the state machine.
     *
     * @return the state machine
     */
    StateMachine<S, E, C> getStateMachine();
}
