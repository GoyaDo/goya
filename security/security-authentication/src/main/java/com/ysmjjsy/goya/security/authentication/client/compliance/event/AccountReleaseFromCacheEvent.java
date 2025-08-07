package com.ysmjjsy.goya.security.authentication.client.compliance.event;

import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * <p>Description: 从账户状态缓存中释放账号事件 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
public class AccountReleaseFromCacheEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 1271351182157593032L;

    private final String data;

    public AccountReleaseFromCacheEvent(String data) {
        super(data);
        this.data = data;
    }

    public AccountReleaseFromCacheEvent(String data, Clock clock) {
        super(data, clock);
        this.data = data;

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
