package com.ysmjjsy.goya.security.authentication.client.compliance;


import com.ysmjjsy.goya.component.pojo.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>Description: 用户错误状态信息 </p>
 *
 * @author goya
 * @since 2022/7/10 16:46
 */
@Getter
@Setter
public class SignInErrorStatus implements Entity {

    @Serial
    private static final long serialVersionUID = -4845373050025248139L;

    private int errorTimes;
    private int remainTimes;
    private Boolean locked;

}
