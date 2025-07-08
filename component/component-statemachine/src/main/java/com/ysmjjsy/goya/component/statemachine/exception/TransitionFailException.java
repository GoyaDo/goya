package com.ysmjjsy.goya.component.statemachine.exception;

/**
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class TransitionFailException extends RuntimeException {

    public TransitionFailException(String errMsg) {
        super(errMsg);
    }
}
