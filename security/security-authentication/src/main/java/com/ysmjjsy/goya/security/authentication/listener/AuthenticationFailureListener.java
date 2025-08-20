package com.ysmjjsy.goya.security.authentication.listener;

import com.ysmjjsy.goya.component.common.exception.stamp.MaximumLimitExceededException;
import com.ysmjjsy.goya.module.identity.manage.UserStatusManager;
import com.ysmjjsy.goya.module.identity.stamp.SignInFailureLimitedStampManager;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.time.Duration;
import java.util.Map;

/**
 * <p>Description: 登出成功监听 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    private final SignInFailureLimitedStampManager stampManager;
    private final UserStatusManager userStatusManager;

    public AuthenticationFailureListener(SignInFailureLimitedStampManager stampManager, UserStatusManager userStatusManager) {
        this.stampManager = stampManager;
        this.userStatusManager = userStatusManager;
    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {

        log.debug("[Goya] |- User sign in catch failure event : [{}].", event.getClass().getName());

        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            Authentication authentication = event.getAuthentication();

            String username = null;

            if (authentication instanceof OAuth2AuthorizationGrantAuthenticationToken token) {

                log.debug("[Goya] |- Toke object in failure event  is OAuth2AuthorizationGrantAuthenticationToken");

                Map<String, Object> params = token.getAdditionalParameters();
                username = getPrincipal(params);
            }

            if (authentication instanceof UsernamePasswordAuthenticationToken token) {

                log.debug("[Goya] |- Toke object in failure event  is UsernamePasswordAuthenticationToken");

                Object principal = token.getPrincipal();
                if (principal instanceof String) {
                    username = (String) principal;
                }
            }

            if (StringUtils.isNotBlank(username)) {

                log.debug("[Goya] |- Parse the user name in failure event is [{}].", username);

                int maxTimes = stampManager.getIdentityProperties().getSignInFailureLimited().getMaxTimes();
                Duration expire = stampManager.getIdentityProperties().getSignInFailureLimited().getExpire();
                try {
                    int times = stampManager.counting(username, maxTimes, expire, true, "AuthenticationFailureListener");
                    log.debug("[Goya] |- Sign in user input password error [{}] items", times);
                } catch (MaximumLimitExceededException e) {
                    log.warn("[Goya] |- User [{}] password error [{}] items, LOCK ACCOUNT!", username, maxTimes);
                    userStatusManager.lock(username);
                }
            }
        }
    }

    private String getPrincipal(Map<String, Object> params) {
        if (MapUtils.isNotEmpty(params)) {
            if (params.containsKey(OAuth2ParameterNames.USERNAME)) {
                Object value = params.get(OAuth2ParameterNames.USERNAME);
                if (ObjectUtils.isNotEmpty(value)) {
                    return (String) value;
                }
            }
        }

        return null;
    }
}
