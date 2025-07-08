package com.ysmjjsy.goya.component.statemachine;

/**
 * Generic strategy interface used by a state machine to respond
 * events by executing an {@code Action} with a {@link StateContext}.
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface Action<S, E, C> {

//    /**
//     * Execute action with a {@link StateContext}.
//     *
//     * @param context the state context
//     */
//    void execute(StateContext<S, E> context);

    public void execute(S from, S to, E event, C context);

}
