package com.ysmjjsy.goya.security.authorization.server.event;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.security.authorization.domain.AttributeTransmitter;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/8 10:08
 */
@Getter
public class AttributeTransmitterChangeEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = -5760389569571817899L;

    private final List<AttributeTransmitter> attributeTransmitters;

    public AttributeTransmitterChangeEvent(Object obj,List<AttributeTransmitter> attributeTransmitters) {
        super(obj);
        this.attributeTransmitters = attributeTransmitters;
    }

    @Override
    public EventType eventType() {
        return null;
    }
}
