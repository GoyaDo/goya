package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * FailCallback
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@FunctionalInterface
public interface FailCallback<S, E, C> {

    /**
     * Callback function to execute if failed to trigger an Event
     *
     * @param sourceState
     * @param event
     * @param context
     */
    void onFail(S sourceState, E event, C context);
}
