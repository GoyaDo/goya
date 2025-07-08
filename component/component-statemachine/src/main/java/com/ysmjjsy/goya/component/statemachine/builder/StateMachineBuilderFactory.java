package com.ysmjjsy.goya.component.statemachine.builder;

/**
 * StateMachineBuilderFactory
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class StateMachineBuilderFactory {
    public static <S, E, C> StateMachineBuilder<S, E, C> create(){
        return new StateMachineBuilderImpl<>();
    }
}
