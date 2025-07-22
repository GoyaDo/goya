package com.ysmjjsy.goya.security.authentication.client.constants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.AREA_PREFIX;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/21 23:34
 */
public interface SecurityClientConstants {

    String REGION_CLIENT_AUTHORIZATION = AREA_PREFIX + "security:client:authorization";
    String REGION_CLIENT_AUTHORIZATION_CONSENT = AREA_PREFIX + "security:client:authorization:consent";
    String REGION_CLIENT_REGISTERED_CLIENT = AREA_PREFIX + "security:client:registered:client";

    String REGION_CLIENT_APPLICATION = AREA_PREFIX + "security:client:application";
    String REGION_CLIENT_COMPLIANCE = AREA_PREFIX + "security:client:compliance";
    String REGION_CLIENT_PERMISSION = AREA_PREFIX + "security:client:permission";
    String REGION_CLIENT_SCOPE = AREA_PREFIX + "security:client:scope";
    String REGION_CLIENT_APPLICATION_SCOPE = AREA_PREFIX + "security:client:application:scope";
    String REGION_CLIENT_PRODUCT = AREA_PREFIX + "security:client:product";
    String REGION_CLIENT_DEVICE = AREA_PREFIX + "security:client:device";
}
