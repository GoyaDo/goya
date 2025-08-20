package com.ysmjjsy.goya.module.identity.user;

import com.ysmjjsy.goya.component.common.pojo.enums.DataItemStatus;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 17:51
 */
public abstract class AbstractUserConverter<T> implements Converter<UserPrincipal,T> {

    /**
     * 判断没有过期工具方法。
     * 如果参数不为空则与当前时间进行比较。如果参数为空，则默认为不过期。
     *
     * @param localDateTime {@link LocalDateTime}
     * @return true 没有过期，false 已过期
     */
    private boolean isNonExpired(LocalDateTime localDateTime) {
        if (ObjectUtils.isEmpty(localDateTime)) {
            return true;
        } else {
            return localDateTime.isAfter(LocalDateTime.now());
        }
    }

    /**
     * 用户是启用还是禁用。禁用的用户进行无法身份验证。
     *
     * @param userPrincipal 系统用户 {@link UserPrincipal}
     * @return true 启用，false 禁用
     */
    protected boolean isEnabled(UserPrincipal userPrincipal) {
        return userPrincipal.status() != DataItemStatus.FORBIDDEN;
    }

    /**
     * 用户的帐户是否已过期。无法对过期的帐户进行身份验证
     * 如果账户的数据状态为 {@link DataItemStatus#EXPIRED}，也认为是过期
     *
     * @param userPrincipal 系统用户 {@link UserPrincipal}
     * @return 如果用户的帐户有效（即未过期），则为true；如果不再有效（即过期），为false
     */
    protected boolean isAccountNonExpired(UserPrincipal userPrincipal) {
        if (userPrincipal.status() == DataItemStatus.EXPIRED) {
            return false;
        }

        return isNonExpired(userPrincipal.accountExpireAt());
    }

    /**
     * 用户是已锁定还是未锁定。无法对锁定的用户进行身份验证。
     *
     * @param userPrincipal 系统用户 {@link UserPrincipal}
     * @return 如果用户未被锁定，则为true，否则为false
     */
    protected boolean isAccountNonLocked(UserPrincipal userPrincipal) {
        return userPrincipal.status() != DataItemStatus.LOCKING;
    }

    /**
     * 用户的凭据（密码）是否已过期。过期的凭据阻止身份验证
     *
     * @param userPrincipal 系统用户 {@link UserPrincipal}
     * @return 如果用户的凭据有效（即未过期），则为true；如果不再有效（即过期），为false
     */
    protected boolean isCredentialsNonExpired(UserPrincipal userPrincipal) {
        return isNonExpired(userPrincipal.credentialsExpireAt());
    }
}
