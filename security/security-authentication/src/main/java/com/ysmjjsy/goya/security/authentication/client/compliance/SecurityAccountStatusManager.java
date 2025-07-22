package com.ysmjjsy.goya.security.authentication.client.compliance;

import com.ysmjjsy.goya.component.dto.enums.DataItemStatus;
import com.ysmjjsy.goya.security.authentication.client.compliance.stamp.LockedUserDetailsStampManager;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import com.ysmjjsy.goya.security.core.service.EnhanceUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 账户锁定处理服务 </p>
 *
 * @author goya
 * @since 2022/7/8 19:25
 */
@Slf4j
@Component
public class SecurityAccountStatusManager {

    private final UserDetailsService userDetailsService;
    private final AccountStatusEventManager accountStatusEventManager;
    private final LockedUserDetailsStampManager lockedUserDetailsStampManager;

    public SecurityAccountStatusManager(UserDetailsService userDetailsService, AccountStatusEventManager accountStatusEventManager, LockedUserDetailsStampManager lockedUserDetailsStampManager) {
        this.userDetailsService = userDetailsService;
        this.lockedUserDetailsStampManager = lockedUserDetailsStampManager;
        this.accountStatusEventManager = accountStatusEventManager;
    }

    private EnhanceUserDetailsService getUserDetailsService() {
        return (EnhanceUserDetailsService) userDetailsService;
    }

    private String getUserId(String username) {
        EnhanceUserDetailsService enhanceUserDetailsService = getUserDetailsService();
        GoyaUser user = enhanceUserDetailsService.loadGoyaUserByUsername(username);
        if (ObjectUtils.isNotEmpty(user)) {
            return user.getUserId();
        }

        log.warn("[Goya] |- Can not found the userid for [{}]", username);
        return null;
    }

    public void lock(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            accountStatusEventManager.postProcess(new UserStatus(userId, DataItemStatus.LOCKING.name()));
            lockedUserDetailsStampManager.put(userId, username);
            log.info("[Goya] |- User count [{}] has been locked, and record into cache!", username);
        }
    }

    public void enable(String userId) {
        if (ObjectUtils.isNotEmpty(userId)) {
            accountStatusEventManager.postProcess(new UserStatus(userId, DataItemStatus.ENABLE.name()));
        }
    }

    public void releaseFromCache(String username) {
        String userId = getUserId(username);
        if (ObjectUtils.isNotEmpty(userId)) {
            String value = lockedUserDetailsStampManager.get(userId);
            if (StringUtils.isNotEmpty(value)) {
                this.lockedUserDetailsStampManager.delete(userId);
                log.info("[Goya] |- User count [{}] locked info has been release!", username);
            }
        }
    }
}
