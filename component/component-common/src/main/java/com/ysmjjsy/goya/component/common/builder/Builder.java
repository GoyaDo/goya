package com.ysmjjsy.goya.component.common.builder;

import java.io.Serializable;

/**
 * <p>Builder 模式抽象接口</p>
 *
 * @author goya
 * @since 2025/7/24 23:32
 */
public interface Builder<T> extends Serializable {

    /**
     * 构建方法
     *
     * @return 构建后的对象
     */
    T build();
}
