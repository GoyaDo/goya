package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * Default fail callback, do nothing.
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class NumbFailCallback<S, E, C> implements FailCallback<S, E, C> {

    @Override
    public void onFail(S sourceState, E event, C context) {
        //do nothing
    }
}
