package com.ysmjjsy.goya.security.authentication.client.domain.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.io.Serial;

/**
 * <p>Description: 保存或更新 RegisteredClient 事件 </p>
 *
 * @author goya
 * @since 2024/3/14 23:49
 */
public class SaveRegisteredClientEvent extends GoyaAbstractEvent<RegisteredClient> {
    @Serial
    private static final long serialVersionUID = 3296957832029439705L;

    public SaveRegisteredClientEvent(RegisteredClient data) {
        super(data);
    }
}
