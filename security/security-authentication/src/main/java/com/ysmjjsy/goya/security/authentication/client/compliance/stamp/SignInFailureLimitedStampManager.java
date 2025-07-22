package com.ysmjjsy.goya.security.authentication.client.compliance.stamp;

import com.ysmjjsy.goya.component.cache.jetcache.stamp.AbstractCountStampManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.SignInErrorStatus;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.crypto.SecureUtil;

/**
 * <p>Description: 登录失败次数限制签章管理 </p>
 *
 * @author goya
 * @since 2022/7/6 23:36
 */
@Getter
public class SignInFailureLimitedStampManager extends AbstractCountStampManager {

    private final SecurityAuthenticationProperties authenticationProperties;

    public SignInFailureLimitedStampManager(SecurityAuthenticationProperties authenticationProperties) {
        super(SecurityConstants.CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED);
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public Long nextStamp(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(authenticationProperties.getSignInFailureLimited().getExpire());
    }

    public SignInErrorStatus errorStatus(String username) {
        int maxTimes = authenticationProperties.getSignInFailureLimited().getMaxTimes();
        Long storedTimes = get(SecureUtil.md5(username));

        int errorTimes = 0;
        if (ObjectUtils.isNotEmpty(storedTimes)) {
            errorTimes = storedTimes.intValue();
        }

        int remainTimes = maxTimes;
        if (errorTimes != 0) {
            remainTimes = maxTimes - errorTimes;
        }

        boolean isLocked = false;
        if (errorTimes == maxTimes) {
            isLocked = true;
        }

        SignInErrorStatus status = new SignInErrorStatus();
        status.setErrorTimes(errorTimes);
        status.setRemainTimes(remainTimes);
        status.setLocked(isLocked);

        return status;
    }
}
