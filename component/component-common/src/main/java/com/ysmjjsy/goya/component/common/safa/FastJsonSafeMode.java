package com.ysmjjsy.goya.component.common.safa;

import org.springframework.beans.factory.InitializingBean;

/**
 * <p>FastJson安全模式，开启后关闭类型隐式传递</p>
 *
 * @author goya
 * @since 2025/7/24 23:44
 */
public class FastJsonSafeMode implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.setProperty("fastjson2.parser.safeMode", "true");
    }
}
