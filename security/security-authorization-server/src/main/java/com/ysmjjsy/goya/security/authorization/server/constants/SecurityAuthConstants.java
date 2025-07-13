package com.ysmjjsy.goya.security.authorization.server.constants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.AREA_PREFIX;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:15
 */
public interface SecurityAuthConstants {

    String AUTH_AREA_PREFIX = AREA_PREFIX + "auth:";

    String REGION_SECURITY_USER = AUTH_AREA_PREFIX + "security:user";
    String REGION_SECURITY_ROLE = AUTH_AREA_PREFIX + "security:role";
    String REGION_SECURITY_DEFAULT_ROLE = AUTH_AREA_PREFIX + "security:defaults:role";
    String REGION_SECURITY_PERMISSION = AUTH_AREA_PREFIX + "security:permission";
    String REGION_SECURITY_OWNERSHIP = AUTH_AREA_PREFIX + "security:ownership";
    String REGION_SECURITY_ELEMENT = AUTH_AREA_PREFIX + "security:element";
    String REGION_SECURITY_SOCIAL_USER = AUTH_AREA_PREFIX + "security:social:user";
    String REGION_SECURITY_DEPARTMENT = AUTH_AREA_PREFIX + "security:department";
    String REGION_SECURITY_EMPLOYEE = AUTH_AREA_PREFIX + "security:employee";
    String REGION_SECURITY_ORGANIZATION = AUTH_AREA_PREFIX + "security:organization";
}
