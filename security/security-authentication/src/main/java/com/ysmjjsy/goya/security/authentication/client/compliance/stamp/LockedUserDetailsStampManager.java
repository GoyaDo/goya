package com.ysmjjsy.goya.security.authentication.client.compliance.stamp;

import com.ysmjjsy.goya.component.cache.jetcache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.dromara.hutool.core.data.id.IdUtil;

/**
 * <p>Description: 锁定账户签章管理 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
public class LockedUserDetailsStampManager extends AbstractStampManager<String, String> {

    private final SecurityAuthenticationProperties authenticationProperties;

    public LockedUserDetailsStampManager(SecurityAuthenticationProperties authenticationProperties) {
        super(SecurityConstants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL);
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(authenticationProperties.getSignInFailureLimited().getExpire());
    }
}
