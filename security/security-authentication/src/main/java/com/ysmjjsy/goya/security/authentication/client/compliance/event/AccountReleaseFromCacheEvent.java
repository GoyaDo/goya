package com.ysmjjsy.goya.security.authentication.client.compliance.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;

import java.io.Serial;
import java.time.Clock;

/**
 * <p>Description: 从账户状态缓存中释放账号事件 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
public class AccountReleaseFromCacheEvent extends GoyaAbstractEvent<String> {

    @Serial
    private static final long serialVersionUID = 1271351182157593032L;

    public AccountReleaseFromCacheEvent(String data) {
        super(data);
    }

    public AccountReleaseFromCacheEvent(String data, Clock clock) {
        super(data, clock);
    }
}
