package com.ysmjjsy.goya.component.cache.constants;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 16:16
 */
public interface CacheConstants {

    /* ---------- 通用缓存常量 ---------- */

    String CACHE_PREFIX = GoyaConstants.GOYA + ":cache:";
    String CACHE_SIMPLE_BASE_PREFIX = CACHE_PREFIX + "simple:";
    String CACHE_TOKEN_BASE_PREFIX = CACHE_PREFIX + "token:";

    String AREA_PREFIX = "data:";
}
