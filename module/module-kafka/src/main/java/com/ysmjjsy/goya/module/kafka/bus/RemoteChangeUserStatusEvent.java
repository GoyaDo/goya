package com.ysmjjsy.goya.module.kafka.bus;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.module.kafka.core.GoyaAbstractRemoteEvent;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: 修改用户状态远程事件 </p>
 *
 * @author goya
 * @since 2022/7/10 16:13
 */
@Getter
public class RemoteChangeUserStatusEvent extends GoyaAbstractRemoteEvent {

    @Serial
    private static final long serialVersionUID = 4187336415759619515L;

    private final String data;

    public RemoteChangeUserStatusEvent(Object source, String originService, String destinationService, String data) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
