package com.ysmjjsy.goya.module.justauth.constants;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_TOKEN_BASE_PREFIX;
import static com.ysmjjsy.goya.component.dto.constants.GoyaConstants.PROPERTY_ENABLED;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:21
 */
public interface JustAuthConstants {

    String PROPERTY_ACCESS_JUSTAUTH = GoyaConstants.GOYA + ".just-auth";
    String ITEM_JUSTAUTH_ENABLED = PROPERTY_ACCESS_JUSTAUTH + PROPERTY_ENABLED;

    String CACHE_NAME_TOKEN_JUSTAUTH = CACHE_TOKEN_BASE_PREFIX + "justauth:";
}
