package com.ysmjjsy.goya.component.core.configuration;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.core.chain.AbstractChainContext;
import com.ysmjjsy.goya.component.core.init.ApplicationContentPostProcessor;
import com.ysmjjsy.goya.component.core.jackson2.Jackson2DefaultObjectMapperBuilderCustomizer;
import com.ysmjjsy.goya.component.core.safa.FastJsonSafeMode;
import com.ysmjjsy.goya.component.core.stragegy.AbstractStrategyChoose;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/12 14:18
 */
@Slf4j
@AutoConfiguration
@Import({
        AsyncConfiguration.class
})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class CoreAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [core] CoreAutoConfiguration auto configure.");
    }

    @Bean
    public ApplicationContentPostProcessor applicationContentPostProcessor() {
        ApplicationContentPostProcessor bean = new ApplicationContentPostProcessor();
        log.trace("[Goya] |- component [core] |- bean [applicationContentPostProcessor] register.");
        return bean;
    }

    @Bean
    public AbstractStrategyChoose abstractStrategyChoose() {
        AbstractStrategyChoose bean = new AbstractStrategyChoose();
        log.trace("[Goya] |- component [core] |- bean [abstractStrategyChoose] register.");
        return bean;
    }

    @Bean
    public AbstractChainContext abstractChainContext() {
        AbstractChainContext bean = new AbstractChainContext();
        log.trace("[Goya] |- component [core] |- bean [abstractChainContext] register.");
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = GoyaConstants.PROPERTY_PREFIX_FASTJSON_SAFA_MODE, havingValue = "true")
    public FastJsonSafeMode goyaFastJsonSafeMode() {
        FastJsonSafeMode bean = new FastJsonSafeMode();
        log.trace("[Goya] |- component [core] |- bean [goyaFastJsonSafeMode] register.");
        return bean;
    }


    @Bean
    public Jackson2ObjectMapperBuilderCustomizer defaultObjectMapperBuilderCustomizer() {
        Jackson2DefaultObjectMapperBuilderCustomizer customizer = new Jackson2DefaultObjectMapperBuilderCustomizer();
        log.trace("[Goya] |- component [core] |- bean [defaultObjectMapperBuilderCustomizer] register.");
        return customizer;
    }

}
