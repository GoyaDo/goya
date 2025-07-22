package com.ysmjjsy.goya.security.core.service;

import com.ysmjjsy.goya.module.identity.domain.AccessPrincipal;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>Description: 系统用户服务策略定义 </p>
 *
 * @author goya
 * @since 2022/2/17 10:52
 */
public interface StrategyUserDetailsService {

    GoyaUser findUserDetailsByUsername(String username) throws AuthenticationException;

    GoyaUser findUserDetailsBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;
}
