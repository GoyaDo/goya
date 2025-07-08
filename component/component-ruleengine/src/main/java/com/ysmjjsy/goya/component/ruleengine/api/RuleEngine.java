package com.ysmjjsy.goya.component.ruleengine.api;

/**
 * <p></p>
 *
 * @author goya
 * @since 2023/5/3 23:12
 */
public interface RuleEngine {
    /**
     * Fire rule on given facts.
     */
    void fire(Rule rule, Facts facts);

}
