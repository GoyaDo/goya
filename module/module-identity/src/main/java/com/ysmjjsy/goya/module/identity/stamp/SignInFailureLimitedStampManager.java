package com.ysmjjsy.goya.module.identity.stamp;

import cn.hutool.v7.crypto.SecureUtil;
import com.ysmjjsy.goya.component.cache.stamp.AbstractCountStampManager;
import com.ysmjjsy.goya.module.identity.configuration.properties.IdentityProperties;
import com.ysmjjsy.goya.module.identity.constants.IdentityConstants;
import com.ysmjjsy.goya.module.identity.domain.SignInErrorStatus;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>登录失败次数限制签章管理</p>
 *
 * @author goya
 * @since 2025/8/19 15:12
 */
public class SignInFailureLimitedStampManager extends AbstractCountStampManager {

    @Getter
    private final IdentityProperties identityProperties;

    public SignInFailureLimitedStampManager(IdentityProperties identityProperties) {
        super(IdentityConstants.CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED);
        this.identityProperties = identityProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(identityProperties.getSignInFailureLimited().getExpire());
    }

    /**
     * 获取登录失败次数
     *
     * @param username 用户名
     * @return {@link SignInErrorStatus}
     */
    public SignInErrorStatus errorStatus(String username) {
        int maxTimes = identityProperties.getSignInFailureLimited().getMaxTimes();
        Long storedTimes = get(SecureUtil.md5(username));

        int errorTimes = 0;
        if (ObjectUtils.isNotEmpty(storedTimes)) {
            errorTimes = storedTimes.intValue();
        }

        int remainTimes = maxTimes;
        if (errorTimes != 0) {
            remainTimes = maxTimes - errorTimes;
        }
        boolean isLocked = errorTimes == maxTimes;
        return new SignInErrorStatus(errorTimes, remainTimes, isLocked);
    }
}
