package com.ysmjjsy.goya.module.identity.manage;

import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.module.identity.event.ChangeUserStatusEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:29
 */
@Slf4j
public class DefaultAccountStatusEventManager implements UserStatusEventManager {

    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getInstance().getAuthServiceName();
    }

    @Override
    public void postLocalProcess(ChangeUserStatusEvent event) {
        publishEvent(event);
    }

    @Override
    public void postRemoteProcess(String event, String originService, String destinationService) {
        log.info("postRemoteProcess:{},originService:{},destinationService:{}", event, originService, destinationService);
    }
}
