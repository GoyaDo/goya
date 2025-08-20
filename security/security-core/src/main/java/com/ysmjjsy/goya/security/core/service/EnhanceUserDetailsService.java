package com.ysmjjsy.goya.security.core.service;

import com.ysmjjsy.goya.module.identity.domain.AccessPrincipal;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: 自定义UserDetailsService接口，方便以后扩展 </p>
 *
 * @author goya
 * @since 2021/1/17 12:49
 */
public interface EnhanceUserDetailsService extends UserDetailsService {

    /**
     * 通过社交集成的唯一id，获取用户信息
     * <p>
     * 如果是短信验证码，openId就是手机号码
     *
     * @param accessPrincipal 社交登录提供的相关信息
     * @param source          社交集成提供商类型
     * @return {@link GoyaUser}
     * @throws UsernameNotFoundException 用户不存在
     */
    GoyaUser loadGoyaUserBySocial(String source, AccessPrincipal accessPrincipal) throws AuthenticationException;

    /**
     * 系统用户名
     *
     * @param username 用户账号
     * @return {@link GoyaUser}
     * @throws UsernameNotFoundException 用户不存在
     */
    GoyaUser loadGoyaUserByUsername(String username) throws UsernameNotFoundException;
}
