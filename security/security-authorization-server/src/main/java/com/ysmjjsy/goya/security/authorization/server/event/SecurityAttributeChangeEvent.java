package com.ysmjjsy.goya.security.authorization.server.event;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;

import java.io.Serial;
import java.time.Clock;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 10:17
 */
public class SecurityAttributeChangeEvent extends GoyaAbstractEvent<SecurityAttribute> {
    @Serial
    private static final long serialVersionUID = 7437300875832574364L;

    public SecurityAttributeChangeEvent(SecurityAttribute data) {
        super(data);
    }

    public SecurityAttributeChangeEvent(SecurityAttribute data, Clock clock) {
        super(data, clock);
    }
}
