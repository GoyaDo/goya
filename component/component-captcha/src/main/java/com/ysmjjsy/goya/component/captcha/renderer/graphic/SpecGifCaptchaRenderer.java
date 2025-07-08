package com.ysmjjsy.goya.component.captcha.renderer.graphic;

import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: Gif 类型验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/20 22:54
 */
@Component
public class SpecGifCaptchaRenderer extends AbstractGifGraphicRenderer {

    @Override
    public String getCategory() {
        return CaptchaCategory.SPEC_GIF.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getCharCharacters();
    }
}
