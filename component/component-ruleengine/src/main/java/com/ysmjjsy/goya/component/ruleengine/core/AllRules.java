package com.ysmjjsy.goya.component.ruleengine.core;

import com.ysmjjsy.goya.component.ruleengine.api.Facts;
import com.ysmjjsy.goya.component.ruleengine.api.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

/**
 * <p></p>
 *
 * @author goya
 * @since 2023/5/3 23:12
 */
public class AllRules  extends CompositeRule{

    private static final Logger LOGGER = LoggerFactory.getLogger(AllRules.class);


    public static CompositeRule allOf(Rule... rules) {
        CompositeRule instance = new AllRules();
        Collections.addAll(instance.rules, rules);
        return instance;
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (rules.stream().allMatch(rule -> rule.evaluate(facts)))
            return true;
        return false;
    }

    @Override
    public void execute(Facts facts) {
        for (Rule rule : rules) {
            rule.execute(facts);
        }
    }

    @Override
    protected boolean doApply(Facts facts) {
        LOGGER.debug("start AND composite rule apply");
        if (evaluate(facts)) {
            for (Rule rule : rules) {
                //所有的rules都执行
                rule.execute(facts);
            }
            return true;
        }
        return false;
    }
}
