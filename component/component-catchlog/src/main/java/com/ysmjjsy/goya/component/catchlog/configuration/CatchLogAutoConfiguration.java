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
public class CatchLogAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [catchlog] CatchLogAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean(CatchLogAspect.class)
    public CatchLogAspect catchLogAspect() {
        CatchLogAspect catchLogAspect = new CatchLogAspect();
        log.trace("[Goya] |- component [catchlog] |- bean [catchLogAspect] register.");
        return catchLogAspect;
    }


}
