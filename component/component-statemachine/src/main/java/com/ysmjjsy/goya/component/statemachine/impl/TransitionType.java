package com.ysmjjsy.goya.component.statemachine.impl;

/**
 * TransitionType
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public enum TransitionType {
    /**
     * Implies that the Transition, if triggered, occurs without exiting or entering the source State
     * (i.e., it does not cause a state change). This means that the entry or exit condition of the source
     * State will not be invoked. An internal Transition can be taken even if the SateMachine is in one or
     * more Regions nested within the associated State.
     */
    INTERNAL,
    /**
     * Implies that the Transition, if triggered, will not exit the composite (source) State, but it
     * will exit and re-enter any state within the composite State that is in the current state configuration.
     */
    LOCAL,
    /**
     * Implies that the Transition, if triggered, will exit the composite (source) State.
     */
    EXTERNAL
}
