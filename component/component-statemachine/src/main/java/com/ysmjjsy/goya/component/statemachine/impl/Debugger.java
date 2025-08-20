package com.ysmjjsy.goya.component.statemachine.impl;

/**
 * Debugger, This is used to decouple Logging framework dependency
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public class Debugger {

    private static boolean isDebugOn = false;

    public static void debug(String message){
        if(isDebugOn){
            System.out.println(message);
        }
    }

    public static void enableDebug(){
        isDebugOn = true;
    }
}
