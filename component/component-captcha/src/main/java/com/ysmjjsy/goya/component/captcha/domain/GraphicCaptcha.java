package com.ysmjjsy.goya.component.captcha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * <p>Description: 图形验证码 </p>
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GraphicCaptcha extends Captcha {

    @Serial
    private static final long serialVersionUID = 5836749262989073901L;

    /**
     * 图形验证码成的图。
     */
    private String graphicImageBase64;
}
