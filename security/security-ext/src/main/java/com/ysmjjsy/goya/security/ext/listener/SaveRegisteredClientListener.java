package com.ysmjjsy.goya.security.ext.listener;

import com.ysmjjsy.goya.security.ext.event.SaveRegisteredClientEvent;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * <p>保存或更新 RegisteredClient 监听器</p>
 *
 * @author goya
 * @since 2025/8/19 22:39
 */
public class SaveRegisteredClientListener implements ApplicationListener<SaveRegisteredClientEvent> {

    private static final Logger log = LoggerFactory.getLogger(SaveRegisteredClientListener.class);

    private final RegisteredClientRepository registeredClientRepository;

    public SaveRegisteredClientListener(RegisteredClientRepository registeredClientRepository) {
        this.registeredClientRepository = registeredClientRepository;
    }

    @Override
    public void onApplicationEvent(SaveRegisteredClientEvent event) {
        RegisteredClient registeredClient = event.getRegisteredClient();

        if (ObjectUtils.isNotEmpty(registeredClient)) {
            log.debug("[Goya] |- Async save or update registered client [{}].", registeredClient.getClientId());
            registeredClientRepository.save(registeredClient);
        }
    }
}
