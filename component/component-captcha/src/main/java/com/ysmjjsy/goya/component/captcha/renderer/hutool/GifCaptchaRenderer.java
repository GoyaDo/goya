package com.ysmjjsy.goya.component.captcha.renderer.hutool;

import com.ysmjjsy.goya.component.captcha.definition.AbstractGraphicRenderer;
import com.ysmjjsy.goya.component.captcha.domain.Metadata;
import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.GifCaptcha;

/**
 * <p>Description: Hutool GIF验证码 </p>
 *
 * @author goya
 * @since 2021/12/23 23:08
 */
public class GifCaptchaRenderer extends AbstractGraphicRenderer {

    @Override
    public Metadata draw() {
        GifCaptcha gifCaptcha = CaptchaUtil.ofGifCaptcha(this.getWidth(), this.getHeight(), this.getLength());
        gifCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(gifCaptcha.getImageBase64Data());
        metadata.setCharacters(gifCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_GIF.getConstant();
    }
}
