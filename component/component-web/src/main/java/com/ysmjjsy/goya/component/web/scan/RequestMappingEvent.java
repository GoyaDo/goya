package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import lombok.Getter;

import java.io.Serial;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 11:36
 */
@Getter
public class RequestMappingEvent extends GoyaAbstractEvent {

    @Serial
    private static final long serialVersionUID = 4795527372613060260L;

    private final List<RequestMapping> requestMappings;

    public RequestMappingEvent(Object source,List<RequestMapping> requestMappings) {
        super(source);
        this.requestMappings = requestMappings;
    }

    @Override
    public EventType eventType() {
        return EventType.PLATFORM_EVENT;
    }
}
