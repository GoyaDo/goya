package com.ysmjjsy.goya.security.authentication.client.compliance.event;


import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authentication.client.compliance.UserStatus;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: 本地用户状态变更事件 </p>
 *
 * @author goya
 * @since 2022/7/10 16:15
 */
@Getter
public class ChangeUserStatusEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -8079484570222103039L;

    private final UserStatus userStatus;

    public ChangeUserStatusEvent(Object source,UserStatus data) {
        super(source);
        this.userStatus = data;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
