package com.ysmjjsy.goya.security.ext.listener;

import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.security.ext.service.SecurityClientComplianceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 16:27
 */
@Slf4j
public class AuthenticationSuccessSaveListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final SecurityClientComplianceService complianceService;

    public AuthenticationSuccessSaveListener(SecurityClientComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        Authentication authentication = event.getAuthentication();

        if (authentication instanceof OAuth2AccessTokenAuthenticationToken authenticationToken) {
            Object details = authentication.getDetails();

            String username = null;
            if (ObjectUtils.isNotEmpty(details) && details instanceof UserPrincipal user) {
                username = user.getName();
            }

            String clientId = authenticationToken.getRegisteredClient().getId();

            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (ObjectUtils.isNotEmpty(requestAttributes) && requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                if (ObjectUtils.isNotEmpty(request) && StringUtils.isNotBlank(username)) {
                    complianceService.save(username, clientId, "用户登录", request);
                }
            } else {
                log.warn("[Goya] |- Can not get request and user name, skip!");
            }
        }
    }
}
