package com.ysmjjsy.goya.security.ext.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import lombok.Getter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.Serial;

/**
 * <p>Description: 保存或更新 RegisteredClient 事件 </p>
 *
 * @author goya
 * @since 2024/3/14 23:49
 */
@Getter
public class SaveRegisteredClientEvent extends GoyaAbstractEvent {
    @Serial
    private static final long serialVersionUID = 3296957832029439705L;

    private final RegisteredClient registeredClient;

    public SaveRegisteredClientEvent(Object source, RegisteredClient registeredClient) {
        super(source);
        this.registeredClient = registeredClient;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
