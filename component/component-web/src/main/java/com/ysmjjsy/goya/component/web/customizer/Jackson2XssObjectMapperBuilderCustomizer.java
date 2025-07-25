package com.ysmjjsy.goya.component.web.customizer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ysmjjsy.goya.component.common.json.jackson2.BaseObjectMapperBuilderCustomizer;
import com.ysmjjsy.goya.component.common.json.jackson2.Jackson2CustomizerOrder;
import com.ysmjjsy.goya.component.web.jackson2.XssStringJsonDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: Jackson Xss Customizer </p>
 *
 * @author goya
 * @since 2023/4/29 16:30
 */
public class Jackson2XssObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

        builder.modulesToInstall(modules -> {
            List<Module> install = new ArrayList<>(modules);
            install.add(simpleModule);
            builder.modulesToInstall(toArray(install));
        });
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_XSS;
    }
}
