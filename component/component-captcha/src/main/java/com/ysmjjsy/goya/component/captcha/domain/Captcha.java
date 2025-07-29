package com.ysmjjsy.goya.component.captcha.domain;

import com.ysmjjsy.goya.component.pojo.domain.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * <p>Description: 验证码返回数据基础类 </p>
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@Data
public abstract class Captcha implements DTO {

    @Serial
    private static final long serialVersionUID = 828995241190608072L;

    @Schema(title = "验证码身份")
    private String identity;

    @Schema(title = "验证码类别")
    private String category;

}
