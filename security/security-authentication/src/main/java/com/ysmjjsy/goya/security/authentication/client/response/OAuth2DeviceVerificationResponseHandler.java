package com.ysmjjsy.goya.security.authentication.client.response;

import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientDeviceService;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2DeviceVerificationAuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

/**
 * <p>Description: 设备验证成功后续逻辑处理器 </p>
 *
 * @author goya
 * @since 2023/5/3 9:35
 */
public class OAuth2DeviceVerificationResponseHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger log = LoggerFactory.getLogger(OAuth2DeviceVerificationResponseHandler.class);

    private final SecurityClientDeviceService securityClientDeviceService;

    public OAuth2DeviceVerificationResponseHandler(SecurityClientDeviceService securityClientDeviceService) {
        super(SecurityConstants.DEVICE_VERIFICATION_SUCCESS_URI);
        this.securityClientDeviceService = securityClientDeviceService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2DeviceVerificationAuthenticationToken deviceVerificationAuthenticationToken =
                (OAuth2DeviceVerificationAuthenticationToken) authentication;

        log.info("[Goya] |- Device verification authentication token is : [{}]", deviceVerificationAuthenticationToken);

        String clientId = deviceVerificationAuthenticationToken.getClientId();

        if (StringUtils.isNotBlank(clientId)) {
            boolean success = securityClientDeviceService.activate(clientId, true);
            log.info("[Goya] |- The activation status of the device is : [{}]", success);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
