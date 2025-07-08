package com.ysmjjsy.goya.component.statemachine;

/**
 * Condition
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface Condition<C> {

    /**
     * @param context context object
     * @return whether the context satisfied current condition
     */
    boolean isSatisfied(C context);

    default String name(){
        return this.getClass().getSimpleName();
    }
}