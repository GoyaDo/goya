package com.ysmjjsy.goya.security.core.configuration;

import com.ysmjjsy.goya.component.common.strategy.Singleton;
import com.ysmjjsy.goya.module.identity.user.StrategyUserService;
import com.ysmjjsy.goya.security.core.advice.SecurityGlobalExceptionHandler;
import com.ysmjjsy.goya.security.core.context.GoyaSecurityContext;
import com.ysmjjsy.goya.security.core.context.GoyaSecurityContextBuilder;
import com.ysmjjsy.goya.security.core.properties.SecurityEndpointProperties;
import com.ysmjjsy.goya.security.core.service.GoyaUserDetailsService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 21:39
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties({SecurityEndpointProperties.class})
@ComponentScan(basePackageClasses = SecurityGlobalExceptionHandler.class)
public class SecurityCoreConfiguration {

    private final GoyaSecurityContext goyaSecurityContext;

    public SecurityCoreConfiguration(SecurityEndpointProperties securityEndpointProperties) {
        this.goyaSecurityContext = GoyaSecurityContextBuilder.builder()
                .endpointProperties(securityEndpointProperties)
                .build();
        Singleton.put(this.goyaSecurityContext);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- security [core] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(StrategyUserService strategyUserService){
        GoyaUserDetailsService goyaUserDetailsService = new GoyaUserDetailsService(strategyUserService);
        log.trace("[Goya] |- security [core] |- bean [userDetailsService] register.");
        return goyaUserDetailsService;
    }
}
