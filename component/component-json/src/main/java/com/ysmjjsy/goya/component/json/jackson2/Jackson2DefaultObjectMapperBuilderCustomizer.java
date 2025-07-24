package com.ysmjjsy.goya.component.json.jackson2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ysmjjsy.goya.component.json.jackson2.modules.EncapsulationClassJackson2Module;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 默认 Jackson Custom 配置 </p>
 *
 * @author goya
 * @since 2023/4/29 11:04
 */
public class Jackson2DefaultObjectMapperBuilderCustomizer implements BaseObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {

        builder.featuresToEnable(
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                JsonParser.Feature.ALLOW_SINGLE_QUOTES,
                JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature()
        );

        builder.featuresToDisable(
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        builder.modulesToInstall(
                modules -> {
                    List<Module> install = new ArrayList<>(modules);
                    install.add(new EncapsulationClassJackson2Module());
                    install.add(new Jdk8Module());
                    install.add(new JavaTimeModule());
                    builder.modulesToInstall(toArray(install));
                }
        );

        builder.findModulesViaServiceLoader(true);
    }

    @Override
    public int getOrder() {
        return Jackson2CustomizerOrder.CUSTOMIZER_DEFAULT;
    }
}
