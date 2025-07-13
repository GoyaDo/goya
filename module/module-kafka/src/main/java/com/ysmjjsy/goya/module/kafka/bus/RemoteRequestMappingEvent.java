package com.ysmjjsy.goya.module.kafka.bus;

import lombok.Getter;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.io.Serial;

/**
 * <p>Request Mapping 收集远程事件</p>
 *
 * @author goya
 * @since 2025/7/11 00:03
 */
@Getter
public class RemoteRequestMappingEvent extends RemoteApplicationEvent {
    @Serial
    private static final long serialVersionUID = 5004257521851814250L;

    private String data;

    public RemoteRequestMappingEvent() {
        super();
    }

    public RemoteRequestMappingEvent(String data, String originService, String destinationService) {
        super(data, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

}
