package com.ysmjjsy.goya.component.statemachine.impl;


import com.ysmjjsy.goya.component.statemachine.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StateHelper
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class StateHelper {
    public static <S, E, C> State<S, E, C> getState(Map<S, State<S, E, C>> stateMap, S stateId){
        State<S, E, C> state = stateMap.get(stateId);
        if (state == null) {
            state = new StateImpl<>(stateId);
            stateMap.put(stateId, state);
        }
        return state;
    }
    public static <C, E, S> List<State<S,E,C>> getStates(Map<S, State<S,E,C>> stateMap, S ... stateIds) {
        List<State<S, E, C>> result = new ArrayList<>();
        for (S stateId : stateIds) {
            State<S, E, C> state = getState(stateMap, stateId);
            result.add(state);
        }
        return result;
    }
}
