package com.ysmjjsy.goya.component.captcha.renderer.graphic;

import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * <p>Description: 中文类型验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/20 22:55
 */
@Component
public class ChineseCaptchaRenderer extends AbstractPngGraphicRenderer {

    @Override
    public String getCategory() {
        return CaptchaCategory.CHINESE.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getWordCharacters();
    }

    @Override
    protected Font getFont() {
        return this.getResourceProvider().getChineseFont();
    }
}
