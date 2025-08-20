package com.ysmjjsy.goya.module.identity.constants;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_TOKEN_BASE_PREFIX;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:44
 */
public interface IdentityConstants {

    String PROPERTY_PREFIX_IDENTITY = GoyaConstants.GOYA + ".identity";

    String CACHE_NAME_TOKEN_LOCKED_USER_DETAIL = CACHE_TOKEN_BASE_PREFIX + "locked:user_details:";
    String CACHE_NAME_TOKEN_SIGN_IN_FAILURE_LIMITED = CACHE_TOKEN_BASE_PREFIX + "sign_in:failure_limited:";


}
