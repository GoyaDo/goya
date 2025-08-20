package com.ysmjjsy.goya.component.statemachine.builder;

import com.ysmjjsy.goya.component.statemachine.Action;

/**
 * When
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface When<S, E, C>{
    /**
     * Define action to be performed during transition
     *
     * @param action performed action
     */
    void perform(Action<S, E, C> action);
}
