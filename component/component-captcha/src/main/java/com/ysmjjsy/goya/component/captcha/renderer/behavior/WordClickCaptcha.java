package com.ysmjjsy.goya.component.captcha.renderer.behavior;

import com.ysmjjsy.goya.component.captcha.domain.Captcha;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <p>Description: 文字点选验证码返回前台信息 </p>
 *
 * @author goya
 * @since 2021/12/14 11:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WordClickCaptcha extends Captcha {

    @Serial
    private static final long serialVersionUID = -6921063680012707315L;

    /**
     * 文字点选验证码生成的带文字背景图。
     */
    private String wordClickImageBase64;

    /**
     * 文字点选验证码文字
     */
    private String words;

    /**
     * 需要点击的文字数量
     */
    private Integer wordsCount;
}
