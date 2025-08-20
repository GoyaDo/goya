package com.ysmjjsy.goya.component.statemachine.impl;


import com.ysmjjsy.goya.component.statemachine.State;
import com.ysmjjsy.goya.component.statemachine.StateMachine;
import com.ysmjjsy.goya.component.statemachine.Transition;
import com.ysmjjsy.goya.component.statemachine.Visitor;
import lombok.extern.slf4j.Slf4j;

/**
 * SysOutVisitor
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
@Slf4j
public class SysOutVisitor implements Visitor {

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        String entry = "-----StateMachine:"+stateMachine.getMachineId()+"-------";
        log.debug(entry);
        return entry;
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        String exit = "------------------------";
        log.debug(exit);
        return exit;
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        String stateStr = "State:"+state.getId();
        sb.append(stateStr).append(LF);
        log.debug(stateStr);
        for(Transition transition: state.getAllTransitions()){
            String transitionStr = "    Transition:"+transition;
            sb.append(transitionStr).append(LF);
            log.debug(transitionStr);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> visitable) {
        return "";
    }
}
