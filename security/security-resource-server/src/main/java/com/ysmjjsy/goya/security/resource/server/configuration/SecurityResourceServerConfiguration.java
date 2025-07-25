package com.ysmjjsy.goya.security.resource.server.configuration;

import com.ysmjjsy.goya.security.authorization.customizer.SecurityAuthorizeHttpRequestsConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityResourceServerConfigurerCustomer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 22:09
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityResourceServerConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- security [security resource server] auto configure.");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            SecurityResourceServerConfigurerCustomer securityResourceServerConfigurerCustomer,
            SecurityAuthorizeHttpRequestsConfigurerCustomer securityAuthorizeHttpRequestsConfigurerCustomer
    ) throws Exception {

        log.debug("[Goya] |- Bean [Resource Server Security Filter Chain] Auto Configure.");

        httpSecurity
                // 禁用明文验证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出页
                .logout(AbstractHttpConfigurer::disable)
                // 关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                // 禁用session
                .sessionManagement(AbstractHttpConfigurer::disable)
        ;

        httpSecurity.authorizeHttpRequests(securityAuthorizeHttpRequestsConfigurerCustomer)
                .oauth2ResourceServer(securityResourceServerConfigurerCustomer);

        return httpSecurity.build();
    }
}
