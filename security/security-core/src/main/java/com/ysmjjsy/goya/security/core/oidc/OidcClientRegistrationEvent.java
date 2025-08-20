package com.ysmjjsy.goya.security.core.oidc;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import lombok.Getter;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.Serial;

/**
 * <p>Description: 客户端注册事件 </p>
 *
 * @author goya
 * @since 2024/3/15 21:21
 */
@Getter
public class OidcClientRegistrationEvent extends GoyaAbstractEvent {
    @Serial
    private static final long serialVersionUID = -4976755990863203408L;

    private final RegisteredClient registeredClient;

    public OidcClientRegistrationEvent(Object source, RegisteredClient registeredClient) {
        super(source);
        this.registeredClient = registeredClient;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
