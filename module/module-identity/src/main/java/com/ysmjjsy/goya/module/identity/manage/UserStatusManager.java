package com.ysmjjsy.goya.module.identity.manage;

import com.ysmjjsy.goya.component.common.pojo.enums.DataItemStatus;
import com.ysmjjsy.goya.module.identity.domain.SignInErrorStatus;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.module.identity.domain.UserStatus;
import com.ysmjjsy.goya.module.identity.event.ChangeUserStatusEvent;
import com.ysmjjsy.goya.module.identity.stamp.LockedUserDetailsStampManager;
import com.ysmjjsy.goya.module.identity.stamp.SignInFailureLimitedStampManager;
import com.ysmjjsy.goya.module.identity.user.StrategyUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:21
 */
@Slf4j
@RequiredArgsConstructor
public class UserStatusManager {

    private final StrategyUserService strategyUserService;
    private final UserStatusEventManager userStatusEventManager;
    private final LockedUserDetailsStampManager lockedUserDetailsStampManager;
    private final SignInFailureLimitedStampManager signInFailureLimitedStampManager;

    /**
     * 根据用户名获取用户ID
     *
     * @param username 用户名
     * @return 用户ID
     */
    public String getUserIdByUsername(String username) {
        UserPrincipal userPrincipal = strategyUserService.loadUserByUsername(username);
        if (Objects.nonNull(userPrincipal)) {
            return userPrincipal.getUserId();
        }
        log.warn("[Goya] |- Can not found the userid for [{}]", username);
        return null;
    }

    /**
     * 用户锁定
     *
     * @param username 用户名
     */
    public void lock(String username) {
        String userId = getUserIdByUsername(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            UserStatus userStatus = new UserStatus(userId, DataItemStatus.LOCKING.name());
            strategyUserService.changeUserStatus(userId, DataItemStatus.LOCKING);
            userStatusEventManager.postProcess(new ChangeUserStatusEvent(this, userStatus));
            lockedUserDetailsStampManager.put(userId, username);
            log.info("[Goya] |- User count [{}] has been locked, and record into cache!", username);
        }
    }

    /**
     * 用户解锁
     *
     * @param userId 用户ID
     */
    public void unlock(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            strategyUserService.changeUserStatus(userId, DataItemStatus.ENABLE);
            userStatusEventManager.postProcess(new ChangeUserStatusEvent(this, new UserStatus(userId, DataItemStatus.ENABLE.name())));
        }
    }

    /**
     * 释放用户锁定信息
     *
     * @param username 用户名
     */
    public void releaseFromCache(String username) {
        String userId = getUserIdByUsername(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            String value = lockedUserDetailsStampManager.get(userId);
            if (StringUtils.isNotEmpty(value)) {
                this.lockedUserDetailsStampManager.delete(userId);
                log.info("[Goya] |- User count [{}] locked info has been release!", username);
            }
        }
    }

    /**
     * 获取用户登录失败信息
     *
     * @param username 用户名
     * @return 用户登录失败信息
     */
    public SignInErrorStatus getSignInErrorStatus(String username) {
        String userId = getUserIdByUsername(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            return signInFailureLimitedStampManager.errorStatus(userId);
        }
        return null;
    }
}
