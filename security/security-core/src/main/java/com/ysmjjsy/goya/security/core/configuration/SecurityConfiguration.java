package com.ysmjjsy.goya.security.core.configuration;

import com.ysmjjsy.goya.security.core.advice.SecurityGlobalExceptionHandler;
import com.ysmjjsy.goya.security.core.context.GoyaSecurityContext;
import com.ysmjjsy.goya.security.core.context.GoyaSecurityContextBuilder;
import com.ysmjjsy.goya.security.core.properties.SecurityEndpointProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

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
public class SecurityConfiguration {

    private final GoyaSecurityContext goyaSecurityContext;

    public SecurityConfiguration(SecurityEndpointProperties securityEndpointProperties) {
        this.goyaSecurityContext = GoyaSecurityContextBuilder.builder()
                .endpointProperties(securityEndpointProperties)
                .build();
        log.info("[Goya] |- Security [security core] Auto Configure.");
    }
}
