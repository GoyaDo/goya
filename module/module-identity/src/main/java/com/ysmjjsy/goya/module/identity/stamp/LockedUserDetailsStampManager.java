package com.ysmjjsy.goya.module.identity.stamp;

import com.ysmjjsy.goya.component.cache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.component.distributedid.utils.SnowflakeIdUtil;
import com.ysmjjsy.goya.module.identity.configuration.properties.IdentityProperties;
import com.ysmjjsy.goya.module.identity.constants.IdentityConstants;

/**
 * <p>锁定账户签章管理</p>
 *
 * @author goya
 * @since 2025/8/19 14:42
 */
public class LockedUserDetailsStampManager extends AbstractStampManager<String, String> {

    private final IdentityProperties identityProperties;

    public LockedUserDetailsStampManager(IdentityProperties identityProperties) {
        super(IdentityConstants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL);
        this.identityProperties = identityProperties;
    }

    @Override
    public String nextStamp(String key) {
        return SnowflakeIdUtil.nextIdStr();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(identityProperties.getSignInFailureLimited().getExpire());
    }
}
