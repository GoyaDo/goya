package com.ysmjjsy.goya.component.ruleengine.core;


import com.ysmjjsy.goya.component.ruleengine.api.Action;
import com.ysmjjsy.goya.component.ruleengine.api.Condition;
import com.ysmjjsy.goya.component.ruleengine.api.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2023/5/3 23:12
 */
public class RuleBuilder {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;

    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return the builder instance
     */
    public RuleBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return the builder instance
     */
    public RuleBuilder description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return the builder instance
     */
    public RuleBuilder priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Set rule condition.
     *
     * @param condition of the rule
     * @return the builder instance
     */
    public RuleBuilder when(Condition condition) {
        this.condition = condition;
        return this;
    }

    /**
     * Add an action to the rule.
     *
     * @param action to add
     * @return the builder instance
     */
    public RuleBuilder then(Action action) {
        this.actions.add(action);
        return this;
    }

    /**
     * Create a new {@link Rule}.
     *
     * @return a new rule instance
     */
    public Rule build() {
        return new DefaultRule(name, description, priority, condition, actions);
    }
}
