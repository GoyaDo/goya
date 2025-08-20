package com.ysmjjsy.goya.security.ext.definition;

import com.ysmjjsy.goya.module.jpa.domain.BaseJpaEntity;
import com.ysmjjsy.goya.module.jpa.domain.BaseJpaRepository;
import com.ysmjjsy.goya.module.rest.service.BaseService;
import com.ysmjjsy.goya.security.ext.event.DeleteRegisteredClientEvent;
import com.ysmjjsy.goya.security.ext.event.SaveRegisteredClientEvent;
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
public abstract class AbstractRegisteredClientService <T extends BaseJpaEntity,R extends BaseJpaRepository<T>> extends BaseService<T, R> {

    protected final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
        applicationEventPublisher.publishEvent(new DeleteRegisteredClientEvent(this,id));
    }

    protected void save(RegisteredClient registeredClient) {
        log.debug("[Goya] |- Async saveOrUpdate registered client [{}].", registeredClient.getClientId());
        applicationEventPublisher.publishEvent(new SaveRegisteredClientEvent(this,registeredClient));
    }
}
