package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import lombok.Getter;

import java.io.Serial;
import java.time.Clock;
import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 11:36
 */
@Getter
public class RequestMappingEvent extends GoyaAbstractEvent<List<RequestMapping>> {

    @Serial
    private static final long serialVersionUID = 4795527372613060260L;

    public RequestMappingEvent(List<RequestMapping> data) {
        super(data);
    }

    public RequestMappingEvent(List<RequestMapping> data, Clock clock) {
        super(data, clock);
    }
}
