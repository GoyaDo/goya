package com.ysmjjsy.goya.module.identity.resolver;

import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;

/**
 * <p>Description: 身份信息解析器 </p>
 *
 * @author goya
 * @since 2022/12/28 0:08
 */
public interface BearerTokenResolver {

    /**
     * 解析 token
     *
     * @param token token
     * @return 用户信息
     */
    UserPrincipal resolve(String token);
}
