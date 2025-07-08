package com.ysmjjsy.goya.component.captcha.renderer.graphic;

import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * <p>Description: 中文Gif类型验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/20 22:55
 */
@Component
public class ChineseGifCaptchaRenderer extends AbstractGifGraphicRenderer {

    @Override
    public String getCategory() {
        return CaptchaCategory.CHINESE_GIF.getConstant();
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
