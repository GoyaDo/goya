package com.ysmjjsy.goya.component.core.utils;

import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import lombok.experimental.UtilityClass;
import org.springframework.boot.autoconfigure.thread.Threading;
import org.springframework.core.env.Environment;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 17:58
 */
@UtilityClass
public class EnvironmentUtils {

    /**
     * <p>是否为虚拟环境</p>
     *
     * @author goya
     * @since 2025/8/12 17:58
     */
    public static boolean isVirtual() {
        return Threading.VIRTUAL.isActive(SpringContextHolder.getBean(Environment.class));
    }
}
