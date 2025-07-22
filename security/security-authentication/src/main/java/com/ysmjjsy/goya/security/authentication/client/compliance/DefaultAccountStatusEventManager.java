package com.ysmjjsy.goya.security.authentication.client.compliance;


import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.ChangeUserStatusEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Description: 用户状态变更处理器 </p>
 *
 * @author goya
 * @since 2022/7/10 17:25
 */
@Slf4j
public class DefaultAccountStatusEventManager implements AccountStatusEventManager {
    @Override
    public String getDestinationServiceName() {
        return GoyaContextHolder.getInstance().getAuthServiceName();
    }

    @Override
    public void postLocalProcess(UserStatus data) {
        publishEvent(new ChangeUserStatusEvent(data));
    }

    @Override
    public void postRemoteProcess(String data, String originService, String destinationService) {
        log.info("originService:{},destinationService:{},data:{}", originService, destinationService, data);
    }
}
