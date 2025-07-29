package com.ysmjjsy.goya.component.captcha.renderer.hutool;

import com.ysmjjsy.goya.component.captcha.definition.AbstractGraphicRenderer;
import com.ysmjjsy.goya.component.captcha.domain.Metadata;
import com.ysmjjsy.goya.component.pojo.enums.CaptchaCategory;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.CircleCaptcha;


/**
 * <p>Description: Hutool圆圈干扰验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/23 23:09
 */
public class CircleCaptchaRenderer extends AbstractGraphicRenderer {

    @Override
    public Metadata draw() {
        CircleCaptcha circleCaptcha = CaptchaUtil.ofCircleCaptcha(this.getWidth(), this.getHeight(), this.getLength(), 20);
        circleCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(circleCaptcha.getImageBase64Data());
        metadata.setCharacters(circleCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_CIRCLE.getConstant();
    }
}
