package com.ysmjjsy.goya.component.json.jackson2.modules;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serial;

/**
 * <p>Description: Java 封装类 Jackson 处理定义 Module </p>
 *
 * @author goya
 * @since 2023/4/29 16:35
 */
public class EncapsulationClassJackson2Module extends SimpleModule {

    @Serial
    private static final long serialVersionUID = -104149029831509018L;

    public EncapsulationClassJackson2Module() {
        this.addSerializer(Long.class, ToStringSerializer.instance);
        this.addSerializer(Long.TYPE, ToStringSerializer.instance);
    }
}
