package com.ysmjjsy.goya.security.authorization.customizer;

import com.ysmjjsy.goya.security.authorization.processor.SecurityAuthorizationManager;
import com.ysmjjsy.goya.security.authorization.processor.SecurityMatcherConfigurer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 23:22
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityAuthorizeHttpRequestsConfigurerCustomer implements Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> {

    private final SecurityMatcherConfigurer securityMatcherConfigurer;
    private final SecurityAuthorizationManager securityAuthorizationManager;


    @Override
    public void customize(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry) {
        registry
                .requestMatchers(securityMatcherConfigurer.getStaticRequestMatchers()).permitAll()
                .requestMatchers(securityMatcherConfigurer.getPermitAllRequestMatchers()).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().access(securityAuthorizationManager);
    }
}
