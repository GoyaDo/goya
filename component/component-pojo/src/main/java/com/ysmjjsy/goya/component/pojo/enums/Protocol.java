package com.ysmjjsy.goya.component.pojo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: Protocol枚举 </p>
 *
 * @author goya
 * @since 2021/6/12 14:48
 */
@Getter
@AllArgsConstructor
public enum Protocol implements GoyaBaseEnum{
    /**
     * 协议类型
     */
    HTTP("http://", "http"),
    HTTPS("https://", "https"),
    REDIS("redis://", "redis"),
    REDISS("rediss://", "rediss");

    private final String format;
    private final String prefix;
}
