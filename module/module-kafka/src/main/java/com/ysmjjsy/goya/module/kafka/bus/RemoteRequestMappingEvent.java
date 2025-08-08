package com.ysmjjsy.goya.module.kafka.bus;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.module.kafka.core.GoyaAbstractRemoteEvent;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Request Mapping 收集远程事件</p>
 *
 * @author goya
 * @since 2025/7/11 00:03
 */
@Getter
public class RemoteRequestMappingEvent extends GoyaAbstractRemoteEvent {
    @Serial
    private static final long serialVersionUID = 5004257521851814250L;

    private final String data;

    public RemoteRequestMappingEvent(Object source, String originService, String destinationService, String data) {
        super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
