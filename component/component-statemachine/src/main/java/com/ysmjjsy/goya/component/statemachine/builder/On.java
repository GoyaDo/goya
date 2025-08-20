package com.ysmjjsy.goya.component.statemachine.builder;


import com.ysmjjsy.goya.component.statemachine.Condition;

/**
 * On
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface On<S, E, C> extends When<S, E, C>{
    /**
     * Add condition for the transition
     * @param condition transition condition
     * @return When clause builder
     */
    When<S, E, C> when(Condition<C> condition);
}
