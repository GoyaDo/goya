package com.ysmjjsy.goya.component.ruleengine.api;

/**
 * <p></p>
 *
 * @author goya
 * @since 2023/5/3 23:12
 */
@FunctionalInterface
public interface Action {
    void execute(Facts facts);
}

