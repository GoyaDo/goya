package com.ysmjjsy.goya.module.identity.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.module.identity.domain.UserStatus;
import lombok.Getter;

/**
 * <p>用户状态变更事件</p>
 *
 * @author goya
 * @since 2025/8/19 14:25
 */
@Getter
public class ChangeUserStatusEvent extends GoyaAbstractEvent {

    private final UserStatus userStatus;

    public ChangeUserStatusEvent(Object source, UserStatus userStatus) {
        super(source);
        this.userStatus = userStatus;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
