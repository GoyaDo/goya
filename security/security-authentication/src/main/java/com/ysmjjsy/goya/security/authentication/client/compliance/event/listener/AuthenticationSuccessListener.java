package com.ysmjjsy.goya.security.authentication.client.compliance.event.listener;

import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import com.ysmjjsy.goya.security.authentication.client.compliance.stamp.SignInFailureLimitedStampManager;
import com.ysmjjsy.goya.security.authentication.client.domain.service.SecurityClientComplianceService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>Description: 登录成功事件监听 </p>
 *
 * @author goya
 * @since 2022/7/7 20:58
 */
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    private final SignInFailureLimitedStampManager stampManager;
    private final SecurityClientComplianceService complianceService;

    public AuthenticationSuccessListener(SignInFailureLimitedStampManager stampManager, SecurityClientComplianceService complianceService) {
        this.stampManager = stampManager;
        this.complianceService = complianceService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();

        if (authentication instanceof OAuth2AccessTokenAuthenticationToken authenticationToken) {
            Object details = authentication.getDetails();

            String username = null;
            if (ObjectUtils.isNotEmpty(details) && details instanceof GoyaUserPrincipal user) {
                username = user.getName();
            }

            String clientId = authenticationToken.getRegisteredClient().getId();

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (ObjectUtils.isNotEmpty(requestAttributes) && requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();

                if (ObjectUtils.isNotEmpty(request) && StringUtils.isNotBlank(username)) {
                    complianceService.save(username, clientId, "用户登录", request);
                    String key = SecureUtil.md5(username);
                    boolean hasKey = stampManager.containKey(key);
                    if (hasKey) {
                        stampManager.delete(key);
                    }
                }
            } else {
                log.warn("[Goya] |- Can not get request and user name, skip!");
            }
        }
    }
}
