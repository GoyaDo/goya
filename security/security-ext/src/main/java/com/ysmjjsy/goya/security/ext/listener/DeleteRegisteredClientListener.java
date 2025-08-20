package com.ysmjjsy.goya.security.ext.listener;

import com.ysmjjsy.goya.security.ext.event.DeleteRegisteredClientEvent;
import com.ysmjjsy.goya.security.ext.service.SecurityAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 17:11
 */
public class DeleteRegisteredClientListener implements ApplicationListener<DeleteRegisteredClientEvent> {

    private static final Logger log = LoggerFactory.getLogger(DeleteRegisteredClientListener.class);

    private final SecurityAuthorizationService securityAuthorizationService;

    public DeleteRegisteredClientListener(SecurityAuthorizationService securityAuthorizationService) {
        this.securityAuthorizationService = securityAuthorizationService;
    }

    @Override
    public void onApplicationEvent(DeleteRegisteredClientEvent event) {
        String id = event.getData();
        log.debug("[Goya] |- Async delete registered client [{}].", id);
        securityAuthorizationService.deleteById(id);
    }
}