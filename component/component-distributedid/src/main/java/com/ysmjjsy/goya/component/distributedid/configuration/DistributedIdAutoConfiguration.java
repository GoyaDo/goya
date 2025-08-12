package com.ysmjjsy.goya.component.distributedid.configuration;

import com.ysmjjsy.goya.component.distributedid.core.snowflake.RandomWorkIdChoose;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>分布式Id 自动配置</p>
 *
 * @author goya
 * @since 2025/7/25 00:25
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class DistributedIdAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [distributedid] DistributedIdAutoConfiguration auto configure.");
    }

    /**
     * 随机数构建雪花 WorkId 选择器。如果项目未使用 Redis，使用该选择器
     */
    @Bean
    @ConditionalOnMissingBean(name = "snowflakeWorkIdChoose")
    public RandomWorkIdChoose snowflakeWorkIdChoose() {
        RandomWorkIdChoose bean = new RandomWorkIdChoose();
        log.trace("[Goya] |- component [distributedid] |- bean [snowflakeWorkIdChoose] register.");
        return bean;
    }
}
