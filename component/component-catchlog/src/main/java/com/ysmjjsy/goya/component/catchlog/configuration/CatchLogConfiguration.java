package com.ysmjjsy.goya.component.catchlog.configuration;

import com.ysmjjsy.goya.component.catchlog.aspect.CatchLogAspect;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 23:46
 */
@Slf4j
@AutoConfiguration
@EnableAspectJAutoProxy
public class CatchLogConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [catchlog] configure.");
    }

    @Bean
    @ConditionalOnMissingBean(CatchLogAspect.class)
    public CatchLogAspect catchLogAspect() {
        return new CatchLogAspect();
    }


}
