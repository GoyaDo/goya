package com.ysmjjsy.goya.component.common.constants;

import cn.hutool.v7.core.regex.RegexPool;

/**
 * <p>常用正则表达式</p>
 *
 * @author goya
 * @since 2025/2/21 10:56
 */
public interface RegexPoolConstants extends RegexPool {

    /**
     * 驼峰转下划线
     * <p>
     * 示例：
     * userName → user_name
     * interfaceId → interface_id
     */
    String CAMEL_TO_UNDERSCORE = "([a-z])([A-Z])";

    /**
     * 匹配大括号及其内容
     * <p>
     * 示例：
     * "ab{gnfnm}ah{hell}o" → 匹配结果：{gnfnm}、{hell}
     */
    String BRACES_AND_CONTENT = "\\{[^}]*\\}";

    /**
     * 匹配所有字符
     * <p>
     * 示例：
     * String cat = "abc";
     * cat.split(ALL_CHARACTERS) → ["a", "b", "c"]
     */
    String ALL_CHARACTERS = "(?!^)";

    /**
     * 单引号字符串等式
     * <p>
     * 示例：
     * pattern='/open/**' → 匹配结果：pattern 和 /open/**
     */
    String SINGLE_QUOTE_STRING_EQUATION = "(\\w+)\\s*=\\s*'(.*?)'";

    /**
     * Bucket DNS 兼容
     * <p>
     * 匹配规则：只能小写字母、数字、点、短横线，且不能以点或短横线结尾
     */
    String DNS_COMPATIBLE = "^[a-z0-9][a-z0-9\\.\\-]+[a-z0-9]$";
}
