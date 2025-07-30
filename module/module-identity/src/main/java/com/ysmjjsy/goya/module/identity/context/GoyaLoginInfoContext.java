package com.ysmjjsy.goya.module.identity.context;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:09
 */
public class GoyaLoginInfoContext {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    public static GoyaUserPrincipal getLoginUser() {
//        return (GoyaUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }
}
