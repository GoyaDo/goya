package com.ysmjjsy.goya.module.identity.domain;

/**
 * <p>用户错误状态信息</p>
 *
 * @author goya
 * @since 2025/8/19 15:14
 */
public record SignInErrorStatus(
        // 错误次数
        int errorTimes,
        // 剩余次数
        int remainTimes,
        // 是否锁定
        boolean locked
) {
}
