package com.ysmjjsy.goya.module.identity.context;

import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import com.ysmjjsy.goya.module.identity.handler.LoginUserHandler;
import lombok.experimental.UtilityClass;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:09
 */
@UtilityClass
public class GoyaLoginInfoContext {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    public static GoyaUserPrincipal getLoginUser() {
        return ApplicationContextHolder.getBean(LoginUserHandler.class).getLoginUser();
    }
}
