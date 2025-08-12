package com.ysmjjsy.goya.component.core.chain;

import org.springframework.core.Ordered;

/**
 * <p>抽象业务责任链组件</p>
 *
 * @author goya
 * @since 2025/7/24 23:32
 */
public interface AbstractChainHandler<T> extends Ordered {

    /**
     * 执行责任链逻辑
     *
     * @param requestParam 责任链执行入参
     */
    void handler(T requestParam);

    /**
     * @return 责任链组件标识
     */
    String mark();
}
