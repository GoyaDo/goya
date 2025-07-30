package com.ysmjjsy.goya.component.pojo.enums;

/**
 * <p>Description: 目标枚举 </p>
 * <p>
 * 统一的目标策略使用枚举。
 *
 * @author goya
 * @since 2022/10/10 19:33
 */
public enum Target implements GoyaBaseEnum{

    /**
     * 目标为服务本地
     */
    LOCAL,

    /**
     * 目标为远程访问
     */
    REMOTE;
}
