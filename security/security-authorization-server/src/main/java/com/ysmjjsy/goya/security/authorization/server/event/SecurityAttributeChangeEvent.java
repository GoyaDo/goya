package com.ysmjjsy.goya.security.authorization.server.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import lombok.Getter;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 10:17
 */
@Getter
public class SecurityAttributeChangeEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 7437300875832574364L;

    private SecurityAttribute securityAttribute;

    public SecurityAttributeChangeEvent(Object obj, SecurityAttribute securityAttribute) {
        super(obj);
        this.securityAttribute = securityAttribute;
    }

    @Override
    public EventType eventType() {
        return EventType.SECURITY_EVENT;
    }
}
