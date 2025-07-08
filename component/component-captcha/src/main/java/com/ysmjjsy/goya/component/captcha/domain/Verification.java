package com.ysmjjsy.goya.component.captcha.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * <p>Description: 验证数据实体 </p>
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Verification extends Captcha {

    @Serial
    private static final long serialVersionUID = -1123879411614917736L;

    /**
     * 滑块拼图验证参数
     */
    private Coordinate coordinate;
    /**
     * 文字点选验证参数
     */
    private List<Coordinate> coordinates;
    /**
     * 图形验证码验证参数
     */
    private String characters;
}
