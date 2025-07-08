package com.ysmjjsy.goya.component.captcha.processor;

import com.ysmjjsy.goya.component.captcha.domain.Captcha;
import com.ysmjjsy.goya.component.captcha.domain.Verification;
import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import com.ysmjjsy.goya.component.exception.captcha.CaptchaCategoryIsIncorrectException;
import com.ysmjjsy.goya.component.exception.captcha.CaptchaHandlerNotExistException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 15:36
 */
@Component
public class CaptchaRendererFactory {

    @Autowired
    private final Map<String, CaptchaRenderer> handlers = new ConcurrentHashMap<>(8);

    public CaptchaRenderer getCaptchaRenderer(String category) {
        CaptchaCategory captchaCategory = CaptchaCategory.getCaptchaCategory(category);

        if (ObjectUtils.isEmpty(captchaCategory)) {
            throw new CaptchaCategoryIsIncorrectException("Captcha category is incorrect.");
        }

        CaptchaRenderer captchaRenderer = handlers.get(captchaCategory.getConstant());
        if (ObjectUtils.isEmpty(captchaRenderer)) {
            throw new CaptchaHandlerNotExistException();
        }

        return captchaRenderer;
    }

    public Captcha getCaptcha(String identity, String category) {
        CaptchaRenderer captchaRenderer = getCaptchaRenderer(category);
        return captchaRenderer.getCaptcha(identity);
    }

    public boolean verify(Verification verification) {
        CaptchaRenderer captchaRenderer = getCaptchaRenderer(verification.getCategory());
        return captchaRenderer.verify(verification);
    }
}
