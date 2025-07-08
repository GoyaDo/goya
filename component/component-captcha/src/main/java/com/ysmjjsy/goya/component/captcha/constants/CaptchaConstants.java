package com.ysmjjsy.goya.component.captcha.constants;

import static com.ysmjjsy.goya.component.cache.constants.CacheConstants.CACHE_TOKEN_BASE_PREFIX;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/2/21 13:29
 */
public interface CaptchaConstants {

    String CACHE_NAME_TOKEN_CAPTCHA = CACHE_TOKEN_BASE_PREFIX + "captcha:";

    String CACHE_NAME_CAPTCHA_JIGSAW = CACHE_NAME_TOKEN_CAPTCHA + "jigsaw:";
    String CACHE_NAME_CAPTCHA_WORD_CLICK = CACHE_NAME_TOKEN_CAPTCHA + "word_click:";
    String CACHE_NAME_CAPTCHA_GRAPHIC = CACHE_NAME_TOKEN_CAPTCHA + "graphic:";
}
