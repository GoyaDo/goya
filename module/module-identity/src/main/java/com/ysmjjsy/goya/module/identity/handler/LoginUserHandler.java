package com.ysmjjsy.goya.module.identity.handler;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/8 09:57
 */
public interface LoginUserHandler {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    GoyaUserPrincipal getLoginUser();
}
