package com.ysmjjsy.goya.security.authentication.client.domain.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;

import java.io.Serial;

/**
 * <p>删除 RegisteredClient 事件</p>
 *
 * @author goya
 * @since 2025/7/21 23:56
 */
public class DeleteRegisteredClientEvent extends GoyaAbstractEvent<String> {
    @Serial
    private static final long serialVersionUID = -8868221614402788460L;

    public DeleteRegisteredClientEvent(String data) {
        super(data);
    }
}
