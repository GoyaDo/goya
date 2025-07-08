package com.ysmjjsy.goya.module.justauth.configuration;

import com.ysmjjsy.goya.component.dto.enums.AccountType;
import com.ysmjjsy.goya.module.justauth.annotation.ConditionalOnJustAuthEnabled;
import com.ysmjjsy.goya.module.justauth.processor.JustAuthProcessor;
import com.ysmjjsy.goya.module.justauth.properties.JustAuthProperties;
import com.ysmjjsy.goya.module.justauth.service.JustAuthService;
import com.ysmjjsy.goya.module.justauth.stamp.JustAuthStateStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: JustAuth配置 </p>
 * <p>
 * goya.platform.social.justauth.configs配置的情况下才注入
 *
 * @author goya
 * @since 2021/5/22 11:25
 */
@AutoConfiguration
@ConditionalOnJustAuthEnabled
@EnableConfigurationProperties(JustAuthProperties.class)
public class JustAuthConfiguration {

    private static final Logger log = LoggerFactory.getLogger(JustAuthConfiguration.class);

    @PostConstruct
    public void init() {
        log.debug("[Goya] |- module [just auth] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public JustAuthStateStampManager justAuthStateStampManager(JustAuthProperties justAuthProperties) {
        JustAuthStateStampManager justAuthStateStampManager = new JustAuthStateStampManager();
        justAuthStateStampManager.setJustAuthProperties(justAuthProperties);
        log.trace("[Goya] |- Bean [Just Auth State Redis Cache] Configure.");
        return justAuthStateStampManager;
    }

    @Bean
    @ConditionalOnBean(JustAuthStateStampManager.class)
    @ConditionalOnMissingBean
    public JustAuthProcessor justAuthProcessor(JustAuthStateStampManager justAuthStateStampManager, JustAuthProperties justAuthProperties) {
        JustAuthProcessor justAuthProcessor = new JustAuthProcessor();
        justAuthProcessor.setJustAuthStateRedisCache(justAuthStateStampManager);
        justAuthProcessor.setJustAuthProperties(justAuthProperties);
        log.trace("[Goya] |- Bean [Just Auth Request Generator] Configure.");
        return justAuthProcessor;
    }

    @Bean(AccountType.JUST_AUTH_HANDLER)
    @ConditionalOnBean(JustAuthProcessor.class)
    @ConditionalOnMissingBean
    public JustAuthService justAuthService(JustAuthProcessor justAuthProcessor) {
        JustAuthService justAuthAccessHandler = new JustAuthService(justAuthProcessor);
        log.debug("[Goya] |- Bean [Just Auth Access Handler] Configure.");
        return justAuthAccessHandler;
    }
}
