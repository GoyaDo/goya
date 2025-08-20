package com.ysmjjsy.goya.security.ext.listener;

import com.ysmjjsy.goya.security.core.oidc.OidcClientRegistrationEvent;
import com.ysmjjsy.goya.security.ext.converter.RegisteredClientToOAuth2DeviceConverter;
import com.ysmjjsy.goya.security.ext.entity.SecurityClientDevice;
import com.ysmjjsy.goya.security.ext.service.SecurityClientDeviceService;
import com.ysmjjsy.goya.security.ext.service.SecurityClientScopeService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <p>Description: 客户端注册监听器 </p>
 *
 * @author goya
 * @since 2024/3/15 21:24
 */
public class OidcClientRegistrationListener implements ApplicationListener<OidcClientRegistrationEvent> {
    private static final Logger log = LoggerFactory.getLogger(OidcClientRegistrationListener.class);

    private final SecurityClientDeviceService securityClientDeviceService;
    private final Converter<RegisteredClient, SecurityClientDevice> toOAuth2DeviceConverter;

    public OidcClientRegistrationListener(SecurityClientDeviceService securityClientDeviceService, SecurityClientScopeService securityClientScopeService) {
        this.securityClientDeviceService = securityClientDeviceService;
        this.toOAuth2DeviceConverter = new RegisteredClientToOAuth2DeviceConverter(securityClientScopeService);
    }

    @Override
    public void onApplicationEvent(OidcClientRegistrationEvent event) {
        RegisteredClient registeredClient = event.getRegisteredClient();

        if (ObjectUtils.isNotEmpty(registeredClient)) {
            SecurityClientDevice oauth2Device = toOAuth2DeviceConverter.convert(registeredClient);
            SecurityClientDevice result = securityClientDeviceService.saveAndFlush(oauth2Device);
            if (ObjectUtils.isNotEmpty(result)) {
                log.error("[Goya] |- Async saveOrUpdate device success");
            }
        }
    }
}
