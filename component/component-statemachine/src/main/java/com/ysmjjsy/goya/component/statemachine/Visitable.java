package com.ysmjjsy.goya.component.statemachine;

/**
 * <p>Visitable </p>
 *
 * @author goya
 * @since : 2023/11/6 13:36
 */
public interface Visitable {
    String accept(final Visitor visitor);
}
