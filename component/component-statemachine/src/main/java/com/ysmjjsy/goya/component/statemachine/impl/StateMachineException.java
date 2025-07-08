package com.ysmjjsy.goya.component.statemachine.impl;

import com.ysmjjsy.goya.component.exception.definition.GoyaRuntimeException;

import java.io.Serial;

/**
 * StateMachineException
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class StateMachineException extends GoyaRuntimeException {
    @Serial
    private static final long serialVersionUID = -6073009767228919390L;

    public StateMachineException(String message){
        super(message);
    }
}
