package com.ysmjjsy.goya.component.common.configuration;

import com.ysmjjsy.goya.component.common.chain.AbstractChainContext;
import com.ysmjjsy.goya.component.common.context.ApplicationContextHolder;
import com.ysmjjsy.goya.component.common.init.ApplicationContentPostProcessor;
import com.ysmjjsy.goya.component.common.safa.FastJsonSafeMode;
import com.ysmjjsy.goya.component.common.strategy.AbstractStrategyChoose;
import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>通用自动配置</p>
 *
 * @author goya
 * @since 2025/6/13 23:43
 */
@Slf4j
@AutoConfiguration
@Import({
        SpringUtil.class,
})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@SuppressWarnings("all")
public class CommonAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [common] CommonAutoConfiguration auto configure.");
    }

    @Bean("goyaApplicationContextHolder")
    public ApplicationContextHolder goyaApplicationContextHolder() {
        ApplicationContextHolder bean = new ApplicationContextHolder();
        log.trace("[Goya] |- component [common] |- bean [goyaApplicationContextHolder] register.");
        return bean;
    }

    @Bean
    public ApplicationContentPostProcessor applicationContentPostProcessor() {
        ApplicationContentPostProcessor bean = new ApplicationContentPostProcessor();
        log.trace("[Goya] |- component [common] |- bean [applicationContentPostProcessor] register.");
        return bean;
    }

    @Bean
    public AbstractStrategyChoose abstractStrategyChoose() {
        AbstractStrategyChoose bean = new AbstractStrategyChoose();
        log.trace("[Goya] |- component [common] |- bean [abstractStrategyChoose] register.");
        return bean;
    }

    @Bean
    public AbstractChainContext abstractChainContext() {
        AbstractChainContext bean = new AbstractChainContext();
        log.trace("[Goya] |- component [common] |- bean [abstractChainContext] register.");
        return bean;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = GoyaConstants.PROPERTY_PREFIX_FASTJSON_SAFA_MODE, havingValue = "true")
    public FastJsonSafeMode goyaFastJsonSafeMode() {
        FastJsonSafeMode bean = new FastJsonSafeMode();
        log.trace("[Goya] |- component [common] |- bean [goyaFastJsonSafeMode] register.");
        return bean;
    }
}
