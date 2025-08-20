package com.ysmjjsy.goya.module.kafka.account;

import com.ysmjjsy.goya.component.bus.event.strategy.ApplicationStrategyEventManager;
import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.module.identity.event.ChangeUserStatusEvent;
import com.ysmjjsy.goya.module.kafka.bus.RemoteChangeUserStatusEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: 用户状态变更处理器 </p>
 *
 * @author goya
 * @since 2022/7/10 17:25
 */
@Slf4j
public class RemoteAccountStatusEventManager implements ApplicationStrategyEventManager<ChangeUserStatusEvent> {
    @Override
    public String getDestinationServiceName() {
        return ServiceContextHolder.getInstance().getAuthServiceName();
    }

    @Override
    public void postLocalProcess(ChangeUserStatusEvent data) {
        publishEvent(data);
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        publishEvent(new RemoteChangeUserStatusEvent(this, originService, destinationService,data));
    }
}
