package com.ysmjjsy.goya.component.captcha.enums;

import com.ysmjjsy.goya.component.common.pojo.enums.GoyaBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 字体资源 </p>
 *
 * @author goya
 * @since 2021/12/21 16:00
 */
@Getter
@AllArgsConstructor
public enum CaptchaFont implements GoyaBaseEnum {
    /**
     * 内置字体类型
     */
    ACTION("Action.ttf"),
    BEATAE("Beatae.ttf"),
    EPILOG("Epilog.ttf"),
    FRESNEL("Fresnel.ttf"),
    HEADACHE("Headache.ttf"),
    LEXOGRAPHER("Lexographer.ttf"),
    PREFIX("Prefix"),
    PROG_BOT("ProgBot"),
    ROBOT_TEACHER("RobotTeacher.ttf"),
    SCANDAL("Scandal.ttf");

    private final String fontName;
}
