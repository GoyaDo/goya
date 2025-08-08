package com.ysmjjsy.goya.security.authentication.client.domain.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>删除 RegisteredClient 事件</p>
 *
 * @author goya
 * @since 2025/7/21 23:56
 */
@Getter
public class DeleteRegisteredClientEvent extends GoyaAbstractEvent {
    @Serial
    private static final long serialVersionUID = -8868221614402788460L;

    private final String data;

    public DeleteRegisteredClientEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
