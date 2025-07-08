package com.ysmjjsy.goya.component.captcha.renderer.graphic;

import com.ysmjjsy.goya.component.dto.enums.CaptchaCategory;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 类型验证码绘制器 </p>
 *
 * @author goya
 * @since 2021/12/20 20:39
 */
@Component
public class SpecCaptchaRenderer extends AbstractPngGraphicRenderer {

    @Override
    public String getCategory() {
        return CaptchaCategory.SPEC.getConstant();
    }

    @Override
    protected String[] getDrawCharacters() {
        return this.getCharCharacters();
    }
}
