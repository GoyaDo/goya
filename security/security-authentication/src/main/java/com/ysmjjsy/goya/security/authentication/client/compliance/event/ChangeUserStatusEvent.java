package com.ysmjjsy.goya.security.authentication.client.compliance.event;


import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authentication.client.compliance.UserStatus;

import java.io.Serial;
import java.time.Clock;

/**
 * <p>Description: 本地用户状态变更事件 </p>
 *
 * @author goya
 * @since 2022/7/10 16:15
 */
public class ChangeUserStatusEvent extends GoyaAbstractEvent<UserStatus> {

    @Serial
    private static final long serialVersionUID = -8079484570222103039L;

    public ChangeUserStatusEvent(UserStatus data) {
        super(data);
    }

    public ChangeUserStatusEvent(UserStatus data, Clock clock) {
        super(data, clock);
    }
}
