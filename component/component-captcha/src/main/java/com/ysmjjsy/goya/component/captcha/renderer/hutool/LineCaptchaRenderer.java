package com.ysmjjsy.goya.component.captcha.renderer.hutool;

import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.LineCaptcha;
import com.ysmjjsy.goya.component.captcha.definition.AbstractGraphicRenderer;
import com.ysmjjsy.goya.component.captcha.domain.Metadata;
import com.ysmjjsy.goya.component.common.pojo.enums.CaptchaCategory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Hutool Line Captcha </p>
 *
 * @author goya
 * @since 2021/12/23 22:44
 */
@Component
public class LineCaptchaRenderer extends AbstractGraphicRenderer {

    @Override
    public Metadata draw() {
        LineCaptcha lineCaptcha = CaptchaUtil.ofLineCaptcha(this.getWidth(), this.getHeight(), this.getLength(), 150);
        lineCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(lineCaptcha.getImageBase64Data());
        metadata.setCharacters(lineCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_LINE.getConstant();
    }
}
