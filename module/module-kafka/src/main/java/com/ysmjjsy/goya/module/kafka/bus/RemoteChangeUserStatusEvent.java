package com.ysmjjsy.goya.module.kafka.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.io.Serial;

/**
 * <p>Description: 修改用户状态远程事件 </p>
 *
 * @author goya
 * @since 2022/7/10 16:13
 */
public class RemoteChangeUserStatusEvent extends RemoteApplicationEvent {

    @Serial
    private static final long serialVersionUID = 4187336415759619515L;

    private String data;

    public RemoteChangeUserStatusEvent() {
        super();
    }

    public RemoteChangeUserStatusEvent(String data, String originService, String destinationService) {
        super(data, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destinationService));
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
