package com.ysmjjsy.goya.component.captcha.enums;

import com.ysmjjsy.goya.component.common.pojo.enums.GoyaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 验证码资源 </p>
 *
 * @author goya
 * @since 2021/12/11 15:27
 */
@Getter
@AllArgsConstructor
public enum CaptchaResource implements GoyaEnum<String> {

    /**
     * 验证码资源类型
     */
    JIGSAW_ORIGINAL("Jigsaw original image", "滑动拼图底图"),
    JIGSAW_TEMPLATE("Jigsaw template image", "滑动拼图滑块底图"),
    WORD_CLICK("Word click image", "文字点选底图");

    private final String content;
    private final String description;

    @Override
    public String getValue() {
        return content;
    }
}
