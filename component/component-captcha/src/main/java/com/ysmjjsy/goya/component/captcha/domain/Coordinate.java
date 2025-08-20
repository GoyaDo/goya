package com.ysmjjsy.goya.component.captcha.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>Description: 坐标 </p>
 *
 * @author goya
 * @since 2025/2/21 10:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate implements Serializable {

    @Serial
    private static final long serialVersionUID = 6882514743363355270L;
    private int x;
    private int y;
}
