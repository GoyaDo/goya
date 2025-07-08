package com.ysmjjsy.goya.component.dto.constants;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 16:16
 */
public interface GoyaConstants {


    String GOYA = "goya";

    String WEBSITE = "https://www.ysmjjsy.goya";
    String LICENSE = "AGPL-3.0 Licensed | Copyright © 2020-2030 Goya";
    String SYSTEM_NAME = "Goya";
    String PACKAGE_NAME = "com.ysmjjsy.goya";

    String OPEN_API_SECURITY_SCHEME_BEARER_NAME = "GOYA_AUTH";

    String PROPERTY_ENABLED = ".enabled";
    String PROPERTY_PREFIX_SPRING = "spring";
    String ITEM_SPRING_APPLICATION_NAME = PROPERTY_PREFIX_SPRING + ".application.name";

    String PROPERTY_PREFIX_CACHE = GOYA + ".cache";
    String PROPERTY_ASSISTANT_CAPTCHA = GoyaConstants.GOYA + ".captcha";
    String PROPERTY_ASSISTANT_CRYPTO = GoyaConstants.GOYA + ".crypto";
    String PROPERTY_ASSISTANT_CRYPTO_STRATEGY = PROPERTY_ASSISTANT_CRYPTO + ".crypto-strategy";

    String PROPERTY_PREFIX_DB = GoyaConstants.GOYA + ".db";
    String PROPERTY_PREFIX_DOC = GoyaConstants.GOYA + ".doc";

    String PROPERTY_PREFIX_WEB = GoyaConstants.GOYA + ".web";

    String PROPERTY_PREFIX_ENDPOINT = GoyaConstants.GOYA + ".endpoint";
    String PROPERTY_PREFIX_PLATFORM = GoyaConstants.GOYA + ".platform";
    String PROPERTY_WEB_SCAN = PROPERTY_PREFIX_WEB + ".scan";
    String PROPERTY_PREFIX_SECURE = PROPERTY_PREFIX_WEB + ".secure";

    String ITEM_SCAN_ENABLED = PROPERTY_WEB_SCAN + PROPERTY_ENABLED;
    String ITEM_PLATFORM_DATA_ACCESS_STRATEGY = GoyaConstants.GOYA + ".data-access-strategy";
    String ITEM_PLATFORM_ARCHITECTURE = GoyaConstants.GOYA + ".architecture";

}
