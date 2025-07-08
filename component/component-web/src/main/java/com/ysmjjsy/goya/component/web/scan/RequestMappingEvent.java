package com.ysmjjsy.goya.component.web.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ysmjjsy.goya.component.event.core.GoyaEvent;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import lombok.Getter;

import java.io.Serial;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/16 11:36
 */
@Getter
public class RequestMappingEvent extends GoyaEvent {

    @Serial
    private static final long serialVersionUID = 4529836785743371402L;

    @JsonProperty("requestMappings")
    private final List<RequestMapping> requestMappings;

    /**
     * 默认构造器 - Jackson反序列化需要
     */
    public RequestMappingEvent() {
        super();
        this.requestMappings = null;
    }

    /**
     * JsonCreator构造器 - 支持Jackson反序列化
     */
    @JsonCreator
    public RequestMappingEvent(@JsonProperty("requestMappings") List<RequestMapping> requestMappings) {
        super();
        this.requestMappings = requestMappings;
    }

    public RequestMappingEvent(Object source, List<RequestMapping> requestMappings) {
        super(source);
        this.requestMappings = requestMappings;
    }

    public RequestMappingEvent(Object source, Map<String, Object> headers, List<RequestMapping> requestMappings) {
        super(source, headers);
        this.requestMappings = requestMappings;
    }
}
