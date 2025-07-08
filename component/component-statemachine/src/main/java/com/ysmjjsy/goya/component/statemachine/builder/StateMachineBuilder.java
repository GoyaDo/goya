package com.ysmjjsy.goya.component.statemachine.builder;


import com.ysmjjsy.goya.component.statemachine.StateMachine;

/**
 * StateMachineBuilder
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface StateMachineBuilder<S, E, C> {
    /**
     * Builder for one transition
     *
     * @return External transition builder
     */
    ExternalTransitionBuilder<S, E, C> externalTransition();

    /**
     * Builder for multiple transitions
     *
     * @return External transition builder
     */
    ExternalTransitionsBuilder<S, E, C> externalTransitions();
    /**
     * Builder for parallel transitions
     *
     * @return External transition builder
     */
    ExternalParallelTransitionBuilder<S, E, C> externalParallelTransition();

    /**
     * Start to build internal transition
     *
     * @return Internal transition builder
     */
    InternalTransitionBuilder<S, E, C> internalTransition();

    /**
     * set up fail callback, default do nothing {@code NumbFailCallbackImpl}
     *
     * @param callback
     */
    void setFailCallback(FailCallback<S, E, C> callback);

    StateMachine<S, E, C> build(String machineId);

}
