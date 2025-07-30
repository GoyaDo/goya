package com.ysmjjsy.goya.security.authentication.client.domain.definition;

import com.ysmjjsy.goya.component.db.domain.BaseService;
import com.ysmjjsy.goya.security.authentication.client.domain.event.DeleteRegisteredClientEvent;
import com.ysmjjsy.goya.security.authentication.client.domain.event.SaveRegisteredClientEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * <p>提取应用和设备重复代码</p>
 *
 * @author goya
 * @since 2025/7/21 23:54
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractRegisteredClientService <T extends BaseJpaAggregate> extends BaseService<T, String> {

    protected final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        applicationEventPublisher.publishEvent(new DeleteRegisteredClientEvent(id));
    }

    protected void save(RegisteredClient registeredClient) {
        log.debug("[Goya] |- Async saveOrUpdate registered client [{}].", registeredClient.getClientId());
        applicationEventPublisher.publishEvent(new SaveRegisteredClientEvent(registeredClient));
    }
}
