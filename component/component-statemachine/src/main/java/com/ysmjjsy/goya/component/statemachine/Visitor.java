package com.ysmjjsy.goya.component.statemachine;

/**
 * <p>Visitor </p>
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface Visitor {

    char LF = '\n';

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnEntry(StateMachine<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnExit(StateMachine<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnEntry(State<?, ?, ?> visitable);

    /**
     * @param visitable the element to be visited.
     * @return
     */
    String visitOnExit(State<?, ?, ?> visitable);
}
