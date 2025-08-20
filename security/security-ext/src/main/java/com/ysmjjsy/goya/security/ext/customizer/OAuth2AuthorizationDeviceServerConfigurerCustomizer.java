package com.ysmjjsy.goya.security.ext.customizer;

import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import com.ysmjjsy.goya.security.core.response.OAuth2AuthenticationFailureResponseHandler;
import com.ysmjjsy.goya.security.ext.response.OAuth2DeviceVerificationResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.Customizer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;

/**
 * <p>Description: OAuth2AuthorizationServerConfigurer 自定义配置 </p>
 *
 * @author goya
 * @since 2024/5/20 12:03
 */
@Slf4j
public class OAuth2AuthorizationDeviceServerConfigurerCustomizer implements Customizer<OAuth2AuthorizationServerConfigurer> {

    private final OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler;
    private final OAuth2AuthenticationFailureResponseHandler oauth2AuthenticationFailureResponseHandler;

    public OAuth2AuthorizationDeviceServerConfigurerCustomizer(OAuth2DeviceVerificationResponseHandler oauth2DeviceVerificationResponseHandler) {
        this.oauth2DeviceVerificationResponseHandler = oauth2DeviceVerificationResponseHandler;
        this.oauth2AuthenticationFailureResponseHandler = new OAuth2AuthenticationFailureResponseHandler();
    }

    @Override
    public void customize(OAuth2AuthorizationServerConfigurer oauth2AuthorizationServerConfigurer) {

        oauth2AuthorizationServerConfigurer.deviceVerificationEndpoint(endpoint -> {
            endpoint.errorResponseHandler(oauth2AuthenticationFailureResponseHandler);
            endpoint.consentPage(SecurityConstants.AUTHORIZATION_CONSENT_URI);
            endpoint.deviceVerificationResponseHandler(oauth2DeviceVerificationResponseHandler);
        });
    }
}
