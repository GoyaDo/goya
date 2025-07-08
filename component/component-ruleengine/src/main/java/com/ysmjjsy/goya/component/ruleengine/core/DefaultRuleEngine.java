package com.ysmjjsy.goya.component.ruleengine.core;

import com.ysmjjsy.goya.component.ruleengine.api.Facts;
import com.ysmjjsy.goya.component.ruleengine.api.Rule;
import com.ysmjjsy.goya.component.ruleengine.api.RuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>
 *
 * @author goya
 * @since 2023/5/3 23:12
 */
public class DefaultRuleEngine implements RuleEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRuleEngine.class);

    @Override
    public void fire(Rule rule, Facts facts) {
        if (rule == null) {
            LOGGER.error("Rules is null! Nothing to apply");
            return;
        }
        rule.apply(facts);
    }
}
