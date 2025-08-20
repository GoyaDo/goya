package com.ysmjjsy.goya.component.statemachine.builder;


import com.ysmjjsy.goya.component.statemachine.exception.TransitionFailException;

/**
 * Alert fail callback, throw an {@code TransitionFailException}
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
public class AlertFailCallback<S, E, C> implements FailCallback<S, E, C> {

    @Override
    public void onFail(S sourceState, E event, C context) {
        throw new TransitionFailException(
            "Cannot fire event [" + event + "] on current state [" + sourceState + "] with context [" + context + "]"
        );
    }
}
