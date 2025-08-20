package com.ysmjjsy.goya.component.statemachine.builder;


import com.ysmjjsy.goya.component.statemachine.Action;
import com.ysmjjsy.goya.component.statemachine.Condition;
import com.ysmjjsy.goya.component.statemachine.State;
import com.ysmjjsy.goya.component.statemachine.Transition;
import com.ysmjjsy.goya.component.statemachine.impl.StateHelper;
import com.ysmjjsy.goya.component.statemachine.impl.TransitionType;

import java.util.List;
import java.util.Map;

/**
 * StateMachineBuilder
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
class ParallelTransitionBuilderImpl<S,E,C> extends AbstractParallelTransitionBuilder<S,E,C> implements ExternalParallelTransitionBuilder<S,E,C> {


    private State<S, E, C> source;
    private List<Transition<S, E, C>> transitions;

    public ParallelTransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        super(stateMap, transitionType);
    }

    @Override
    public ParallelFrom<S, E, C> from(S stateId) {
        source = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public When<S, E, C> when(Condition<C> condition) {
        for (Transition<S, E, C> transition : transitions) {
            transition.setCondition(condition);
        }
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transitions = source.addTransitions(event, targets, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        for (Transition<S, E, C> transition : transitions) {
            transition.setAction(action);
        }
    }


}
