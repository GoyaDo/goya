package com.ysmjjsy.goya.component.common.strategy;

/**
 * <p>策略执行抽象</p>
 *
 * @author goya
 * @since 2025/7/24 23:39
 */
public interface AbstractExecuteStrategy<REQUEST, RESPONSE> extends AbstractStrategy{

    /**
     * 执行策略
     *
     * @param requestParam 执行策略入参
     */
    default void execute(REQUEST requestParam) {

    }

    /**
     * 执行策略，带返回值
     *
     * @param requestParam 执行策略入参
     * @return 执行策略后返回值
     */
    default RESPONSE executeResp(REQUEST requestParam) {
        return null;
    }
}
