package com.ysmjjsy.goya.module.identity.user;

import com.ysmjjsy.goya.component.common.pojo.enums.DataItemStatus;
import com.ysmjjsy.goya.module.identity.domain.AccessPrincipal;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 15:37
 */
public class DefaultStrategyUserService implements StrategyUserService{

    @Override
    public UserPrincipal loadUserByUsername(String username) {
        return null;
    }

    @Override
    public UserPrincipal loadUserById(String userId) {
        return null;
    }

    @Override
    public boolean changeUserStatus(String userId, DataItemStatus status) {
        return false;
    }

    @Override
    public UserPrincipal loadUserBySocial(String source, AccessPrincipal accessPrincipal) {
        return null;
    }
}
