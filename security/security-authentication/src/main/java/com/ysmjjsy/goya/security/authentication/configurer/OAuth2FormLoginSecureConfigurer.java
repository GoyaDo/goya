package com.ysmjjsy.goya.security.authentication.configurer;

import com.ysmjjsy.goya.component.captcha.processor.CaptchaRendererFactory;
import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.authentication.provider.OAuth2FormLoginAuthenticationProvider;
import com.ysmjjsy.goya.security.authentication.response.OAuth2FormLoginAuthenticationFailureHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * <p>Description: OAuth2 Form Login Configurer </p>
 * <p>
 * 使用此种方式，相当于额外增加了一种表单登录方式。因此对原有的 http.formlogin进行的配置，对当前此种方式的配置并不生效。
 *
 * @author goya
 * @since 2022/4/12 13:29
 * @see org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer
 */
public class OAuth2FormLoginSecureConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<OAuth2FormLoginSecureConfigurer<H>, H> {

    private final UserDetailsService userDetailsService;
    private final SecurityAuthenticationProperties authenticationProperties;
    private final CaptchaRendererFactory captchaRendererFactory;
    private final HttpCryptoProcessor httpCryptoProcessor;

    public OAuth2FormLoginSecureConfigurer(UserDetailsService userDetailsService, SecurityAuthenticationProperties authenticationProperties, CaptchaRendererFactory captchaRendererFactory, HttpCryptoProcessor httpCryptoProcessor) {
        this.userDetailsService = userDetailsService;
        this.authenticationProperties = authenticationProperties;
        this.captchaRendererFactory = captchaRendererFactory;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public void configure(H httpSecurity) throws Exception {

        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        SecurityContextRepository securityContextRepository = httpSecurity.getSharedObject(SecurityContextRepository.class);

        OAuth2FormLoginAuthenticationFilter filter = getOAuth2FormLoginAuthenticationFilter(authenticationManager, this.httpCryptoProcessor, securityContextRepository);

        OAuth2FormLoginAuthenticationProvider provider = new OAuth2FormLoginAuthenticationProvider(captchaRendererFactory);
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        httpSecurity
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }


    private OAuth2FormLoginAuthenticationFilter getOAuth2FormLoginAuthenticationFilter(AuthenticationManager authenticationManager, HttpCryptoProcessor httpCryptoProcessor, SecurityContextRepository securityContextRepository) {
        OAuth2FormLoginAuthenticationFilter filter = new OAuth2FormLoginAuthenticationFilter(authenticationManager, httpCryptoProcessor);
        filter.setUsernameParameter(getFormLogin().getUsernameParameter());
        filter.setPasswordParameter(getFormLogin().getPasswordParameter());
        filter.setAuthenticationDetailsSource(new OAuth2FormLoginWebAuthenticationDetailSource(authenticationProperties, httpCryptoProcessor));
        filter.setAuthenticationFailureHandler(new OAuth2FormLoginAuthenticationFailureHandler(getFormLogin().getFailureUrl()));
        filter.setSecurityContextRepository(securityContextRepository);
        return filter;
    }

    private SecurityAuthenticationProperties.FormLogin getFormLogin() {
        return authenticationProperties.getFormLogin();
    }
}
