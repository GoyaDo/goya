package com.ysmjjsy.goya.module.identity.user;

import com.ysmjjsy.goya.component.common.pojo.enums.DataItemStatus;
import com.ysmjjsy.goya.module.identity.domain.AccessPrincipal;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:38
 */
public interface StrategyUserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserPrincipal loadUserByUsername(String username);

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    UserPrincipal loadUserById(String userId);

    /**
     * 用户状态变更
     *
     * @param userId 用户ID
     * @param status 用户状态
     * @return 是否成功
     */
    boolean changeUserStatus(String userId, DataItemStatus status);

    /**
     * 根据社交平台获取用户信息
     *
     * @param source      社交平台
     * @param accessPrincipal 访问凭证
     * @return 用户信息
     */
    UserPrincipal loadUserBySocial(String source, AccessPrincipal accessPrincipal);

}
