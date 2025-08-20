package com.ysmjjsy.goya.component.statemachine.builder;


import com.ysmjjsy.goya.component.statemachine.Action;
import com.ysmjjsy.goya.component.statemachine.Condition;
import com.ysmjjsy.goya.component.statemachine.State;
import com.ysmjjsy.goya.component.statemachine.Transition;
import com.ysmjjsy.goya.component.statemachine.impl.StateHelper;
import com.ysmjjsy.goya.component.statemachine.impl.TransitionType;

import java.util.Map;

/**
 * TransitionBuilderImpl
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
class TransitionBuilderImpl<S,E,C> extends AbstractTransitionBuilder<S,E,C> implements ExternalTransitionBuilder<S,E,C>, InternalTransitionBuilder<S,E,C> {


    private State<S, E, C> source;
    private Transition<S, E, C> transition;

    public TransitionBuilderImpl(Map<S, State<S, E, C>> stateMap, TransitionType transitionType) {
        super(stateMap, transitionType);
    }

    @Override
    public From<S, E, C> from(S stateId) {
        source = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public To<S, E, C> within(S stateId) {
        source = target = StateHelper.getState(stateMap, stateId);
        return this;
    }

    @Override
    public When<S, E, C> when(Condition<C> condition) {
        transition.setCondition(condition);
        return this;
    }

    @Override
    public On<S, E, C> on(E event) {
        transition = source.addTransition(event, target, transitionType);
        return this;
    }

    @Override
    public void perform(Action<S, E, C> action) {
        transition.setAction(action);
    }


}
