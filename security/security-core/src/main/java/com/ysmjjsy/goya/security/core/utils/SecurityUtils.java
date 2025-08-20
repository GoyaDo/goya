package com.ysmjjsy.goya.security.core.utils;

import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 17:57
 */
public class SecurityUtils {

    public static final String PREFIX_ROLE = "ROLE_";
    public static final String PREFIX_SCOPE = "SCOPE_";
    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 密码加密
     *
     * @param password 明文密码
     * @return 已加密密码
     */
    public static String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 密码验证
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public static SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        return ObjectUtils.isNotEmpty(getAuthentication()) && getAuthentication().isAuthenticated();
    }

    public static Object getDetails() {
        return getAuthentication().getDetails();
    }

    /**
     * 当用户角色发生变化，或者用户角色对应的权限发生变化，那么就从数据库中重新查询用户相关信息
     *
     * @param newGoyaUser 从数据库中重新查询并生成的用户信息
     */
    public static void reloadAuthority(GoyaUser newGoyaUser) {
        // 重新new一个token，因为Authentication中的权限是不可变的.
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                newGoyaUser, newGoyaUser.getPassword(),
                newGoyaUser.getAuthorities());
        token.setDetails(getDetails());
        getSecurityContext().setAuthentication(token);
    }

    public static String wellFormRolePrefix(String content) {
        return wellFormPrefix(content, PREFIX_ROLE);
    }

    public static String wellFormPrefix(String content, String prefix) {
        if (StringUtils.startsWith(content, prefix)) {
            return content;
        } else {
            return prefix + content;
        }
    }
}
