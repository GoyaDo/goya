package com.ysmjjsy.goya.security.authentication.response;

import com.ysmjjsy.goya.security.authentication.client.domain.event.OidcClientRegistrationEvent;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.oidc.http.converter.OidcClientRegistrationHttpMessageConverter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * <p>Description: 客户端自动注册成功后续逻辑处理器 </p>
 *
 * @author goya
 * @since 2023/5/23 17:37
 */
public class OidcClientRegistrationResponseHandler implements AuthenticationSuccessHandler, ApplicationEventPublisherAware {

    private static final Logger log = LoggerFactory.getLogger(OidcClientRegistrationResponseHandler.class);

    private final RegisteredClientRepository registeredClientRepository;
    private ApplicationEventPublisher applicationEventPublisher;

    private final HttpMessageConverter<OidcClientRegistration> clientRegistrationHttpMessageConverter =
            new OidcClientRegistrationHttpMessageConverter();

    public OidcClientRegistrationResponseHandler(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OidcClientRegistrationAuthenticationToken clientRegistrationAuthenticationToken =
                (OidcClientRegistrationAuthenticationToken) authentication;

        OidcClientRegistration clientRegistration = clientRegistrationAuthenticationToken.getClientRegistration();
        RegisteredClient registeredClient = registeredClientRepository.findByClientId(clientRegistration.getClientId());
        if (ObjectUtils.isNotEmpty(registeredClient) && StringUtils.isNotBlank(clientRegistration.getRegistrationAccessToken())) {
            log.debug("[Goya] |- Sync oidcClientRegistration to device.");
            applicationEventPublisher.publishEvent(new OidcClientRegistrationEvent(this,registeredClient));
        }

        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            httpResponse.setStatusCode(HttpStatus.CREATED);
        } else {
            httpResponse.setStatusCode(HttpStatus.OK);
        }
        this.clientRegistrationHttpMessageConverter.write(clientRegistration, null, httpResponse);
    }
}
