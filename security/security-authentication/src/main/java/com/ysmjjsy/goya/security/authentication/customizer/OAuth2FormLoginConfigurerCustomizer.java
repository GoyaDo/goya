package com.ysmjjsy.goya.security.authentication.customizer;

import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;

/**
 * <p>Description: FormLoginConfigurer 扩展配置 </p>
 *
 * @author goya
 * @since 2023/9/1 8:45
 */
@RequiredArgsConstructor
public class OAuth2FormLoginConfigurerCustomizer implements Customizer<FormLoginConfigurer<HttpSecurity>> {

    private final SecurityAuthenticationProperties authenticationProperties;

    @Override
    public void customize(FormLoginConfigurer<HttpSecurity> configurer) {
        configurer
                .loginPage(getFormLogin().getLoginPageUrl())
                .usernameParameter(getFormLogin().getUsernameParameter())
                .passwordParameter(getFormLogin().getPasswordParameter());

        if (StringUtils.isNotBlank(getFormLogin().getFailureUrl())) {
            configurer.failureForwardUrl(getFormLogin().getFailureUrl());
        }
        if (StringUtils.isNotBlank(getFormLogin().getAuthenticationUrl())) {
            configurer.successForwardUrl(getFormLogin().getAuthenticationUrl());
        }
    }

    private SecurityAuthenticationProperties.FormLogin getFormLogin() {
        return authenticationProperties.getFormLogin();
    }
}
