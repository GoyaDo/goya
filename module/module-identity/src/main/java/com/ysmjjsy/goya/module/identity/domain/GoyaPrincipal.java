package com.ysmjjsy.goya.module.identity.domain;

import java.security.Principal;

/**
 * <p>Description: Goya 平台用户 Principal 统一定义 </p>
 *
 * @author goya
 * @since 2024/4/8 11:54
 */
public interface GoyaPrincipal extends Principal {

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getUserId();

    /**
     * 获取用户头像
     *
     * @return 用户头像
     */
    String getAvatar();
}
