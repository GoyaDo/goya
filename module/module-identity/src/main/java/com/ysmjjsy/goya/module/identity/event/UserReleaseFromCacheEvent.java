package com.ysmjjsy.goya.module.identity.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import lombok.Getter;

import java.io.Serial;

/**
 * <p>Description: 从账户状态缓存中释放账号事件 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
@Getter
public class UserReleaseFromCacheEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 1271351182157593032L;

    private final String data;

    public UserReleaseFromCacheEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
