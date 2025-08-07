package com.ysmjjsy.goya.security.authentication.client.compliance.event;


import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authentication.client.compliance.UserStatus;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p>Description: 本地用户状态变更事件 </p>
 *
 * @author goya
 * @since 2022/7/10 16:15
 */
public class ChangeUserStatusEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -8079484570222103039L;

    private final UserStatus userStatus;

    public ChangeUserStatusEvent(UserStatus data) {
        super(data);
        this.userStatus = data;
    }

    public ChangeUserStatusEvent(UserStatus data, Clock clock) {
        super(data, clock);
        this.userStatus = data;
    }

    @Override
    public EventType eventType() {
        return null;
    }

    @Override
    public LocalDateTime getCreateTime() {
        return null;
    }

    @Override
    public void setEventStatus(EventStatus eventStatus) {

    }
}
