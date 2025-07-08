package com.ysmjjsy.goya.component.web.constants;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_TOKEN_BASE_PREFIX;
import static com.ysmjjsy.goya.component.dto.constants.GoyaConstants.PROPERTY_ENABLED;

/**
 * <p>Description: Web 相关模块通用常量 </p>
 *
 * @author goya
 * @since 2024/1/24 17:19
 */
public interface WebConstants {

    String[] SESSION_IDS = new String[]{"JSESSIONID, SESSION"};


    String CACHE_NAME_TOKEN_IDEMPOTENT = CACHE_TOKEN_BASE_PREFIX + "idempotent:";
    String CACHE_NAME_TOKEN_ACCESS_LIMITED = CACHE_TOKEN_BASE_PREFIX + "access_limited:";
    String CACHE_NAME_TOKEN_SECURE_KEY = CACHE_TOKEN_BASE_PREFIX + "secure_key:";


}
