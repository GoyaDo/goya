package com.ysmjjsy.goya.security.authentication.client.domain.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.Serial;

/**
 * <p>Description: 客户端注册事件 </p>
 *
 * @author goya
 * @since 2024/3/15 21:21
 */
public class OidcClientRegistrationEvent extends GoyaAbstractEvent<RegisteredClient> {
    @Serial
    private static final long serialVersionUID = -4976755990863203408L;

    public OidcClientRegistrationEvent(RegisteredClient data) {
        super(data);
    }
}
