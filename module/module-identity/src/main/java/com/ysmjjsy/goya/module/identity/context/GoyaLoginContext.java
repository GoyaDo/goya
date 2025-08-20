package com.ysmjjsy.goya.module.identity.context;

import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.module.identity.handler.LoginHandler;
import lombok.experimental.UtilityClass;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:09
 */
@UtilityClass
public class GoyaLoginContext {

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    public static UserPrincipal getLoginUser() {
        return SpringContextHolder.getBean(LoginHandler.class).getLoginUser();
    }
}
