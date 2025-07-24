package com.ysmjjsy.goya.component.distributedid.core;

/**
 * <p>ID 生成器</p>
 *
 * @author goya
 * @since 2025/7/25 00:29
 */
public interface IdGenerator {

    /**
     * 下一个 ID
     */
    default long nextId() {
        return 0L;
    }

    /**
     * 下一个 ID 字符串
     */
    default String nextIdStr() {
        return "";
    }
}
