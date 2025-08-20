package com.ysmjjsy.goya.security.authorization.server.configuration;

import com.ysmjjsy.goya.component.captcha.processor.CaptchaRendererFactory;
import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.security.authentication.configurer.OAuth2FormLoginSecureConfigurer;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.authentication.response.DefaultOAuth2AuthenticationEventPublisher;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityAuthorizeHttpRequestsConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.customizer.SecurityResourceServerConfigurerCustomer;
import com.ysmjjsy.goya.security.authorization.response.GoyaAccessDeniedHandler;
import com.ysmjjsy.goya.security.authorization.response.GoyaAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 23:02
 */
@Slf4j
@AutoConfiguration
@EnableWebSecurity
public class DefaultSecurityAutoConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity httpSecurity,
            UserDetailsService userDetailsService,
            HttpCryptoProcessor httpCryptoProcessor,
            SecurityAuthenticationProperties authenticationProperties,
            CaptchaRendererFactory captchaRendererFactory,
            SecurityResourceServerConfigurerCustomer oauth2ResourceServerConfigurerCustomer,
            SecurityAuthorizeHttpRequestsConfigurerCustomer oauth2AuthorizeHttpRequestsConfigurerCustomer
    ) throws Exception {

        log.debug("[Goya] |- Bean [Default Security Filter Chain] Auto Configure.");
        // 禁用CSRF 开启跨域
        httpSecurity.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable);

        // @formatter:off
        httpSecurity
                .authorizeHttpRequests(oauth2AuthorizeHttpRequestsConfigurerCustomer)
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint(new GoyaAuthenticationEntryPoint());
                    exceptions.accessDeniedHandler(new GoyaAccessDeniedHandler());
                })
                .oauth2ResourceServer(oauth2ResourceServerConfigurerCustomer)
                .with(new OAuth2FormLoginSecureConfigurer<>(userDetailsService, authenticationProperties, captchaRendererFactory, httpCryptoProcessor), (configurer) -> {});

        // @formatter:on
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationContext applicationContext) {
        DefaultOAuth2AuthenticationEventPublisher publisher = new DefaultOAuth2AuthenticationEventPublisher(applicationContext);
        // 设置默认的错误 Event 类型。在遇到默认字典中没有的错误时，默认抛出。
        publisher.setDefaultAuthenticationFailureEvent(AuthenticationFailureBadCredentialsEvent.class);
        log.debug("[Goya] |- Bean [Authentication Event Publisher] Auto Configure.");
        return publisher;
    }
}
