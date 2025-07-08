package com.ysmjjsy.goya.security.core.enums;

/**
 * <p>证书使用策略</p>
 *
 * @author goya
 * @since 2025/7/8 22:46
 */
public enum Certificate {

    /**
     * Spring Authorization Server 默认的 JWK 生成方式
     */
    STANDARD,
    /**
     * 自定义证书 JWK 生成方式
     */
    CUSTOM;
}
