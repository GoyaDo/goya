package com.ysmjjsy.goya.module.sms.constants;

import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_TOKEN_BASE_PREFIX;
import static com.ysmjjsy.goya.component.dto.constants.GoyaConstants.PROPERTY_ENABLED;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:11
 */
public interface SmsConstants {

    String PROPERTY_PREFIX_SMS = GoyaConstants.GOYA + ".sms";
    String ITEM_SMS_ENABLED = PROPERTY_PREFIX_SMS + PROPERTY_ENABLED;

    String CACHE_NAME_TOKEN_VERIFICATION_CODE = CACHE_TOKEN_BASE_PREFIX + "verification:";

}
