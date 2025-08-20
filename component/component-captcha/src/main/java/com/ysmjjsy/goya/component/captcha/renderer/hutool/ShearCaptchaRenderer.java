package com.ysmjjsy.goya.component.captcha.renderer.hutool;

import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.ShearCaptcha;
import com.ysmjjsy.goya.component.captcha.definition.AbstractGraphicRenderer;
import com.ysmjjsy.goya.component.captcha.domain.Metadata;
import com.ysmjjsy.goya.component.common.pojo.enums.CaptchaCategory;

/**
 * <p>Description: Hutool扭曲干扰验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/23 23:08
 */
public class ShearCaptchaRenderer extends AbstractGraphicRenderer {

    @Override
    public Metadata draw() {
        ShearCaptcha shearCaptcha = CaptchaUtil.ofShearCaptcha(this.getWidth(), this.getHeight(), this.getLength(), 4);
        shearCaptcha.setFont(this.getFont());

        Metadata metadata = new Metadata();
        metadata.setGraphicImageBase64(shearCaptcha.getImageBase64Data());
        metadata.setCharacters(shearCaptcha.getCode());
        return metadata;
    }

    @Override
    public String getCategory() {
        return CaptchaCategory.HUTOOL_SHEAR.getConstant();
    }
}
