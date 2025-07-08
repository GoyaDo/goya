package com.ysmjjsy.goya.module.identity.resolver;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;

/**
 * <p>Description: 身份信息解析器 </p>
 *
 * @author goya
 * @since 2022/12/28 0:08
 */
public interface BearerTokenResolver {

    GoyaUserPrincipal resolve(String token);
}
