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
public class NaturalRules extends CompositeRule{
    private static final Logger LOGGER = LoggerFactory.getLogger(NaturalRules.class);

    public static CompositeRule of(Rule... rules) {
        CompositeRule instance = new NaturalRules();
        Collections.addAll(instance.rules, rules);
        return instance;
    }

    @Override
    public boolean evaluate(Facts facts) {
        //不支持, which means Natural Rules can not be the children of other rules
        throw new RuntimeException("evaluate not supported for natural composite");
    }

    @Override
    public void execute(Facts facts) {
        //不支持, which means Natural Rules can not be the children of other rules
        throw new RuntimeException("execute not supported for natural composite");
    }

    @Override
    protected boolean doApply(Facts facts) {
        LOGGER.debug("start Natural composite rule apply");
        for (Rule rule : rules) {
            rule.apply(facts);
        }
        return true;
    }
}
