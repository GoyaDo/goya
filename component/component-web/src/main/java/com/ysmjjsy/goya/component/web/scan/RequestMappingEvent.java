package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.enums.EventType;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import lombok.Getter;

import java.io.Serial;
import java.time.Clock;
import java.time.LocalDateTime;
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

    private final List<RequestMapping> data;

    public RequestMappingEvent(List<RequestMapping> data) {
        super(data);
        this.data = data;
    }

    public RequestMappingEvent(List<RequestMapping> data, Clock clock) {
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
