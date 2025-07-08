package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.exception.definition.GoyaDefaultException;
import lombok.Getter;

import java.io.Serial;

/**
 * 事件处理异常
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Getter
public class EventHandleException extends GoyaDefaultException {

    @Serial
    private static final long serialVersionUID = -6853670465465467740L;

    private final String eventId;
    private final String listenerType;

    public EventHandleException(String eventId, String message) {
        super(message);
        this.eventId = eventId;
        this.listenerType = null;
    }

    public EventHandleException(String eventId, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.listenerType = null;
    }

    public EventHandleException(String eventId, String listenerType, String message, Throwable cause) {
        super(message, cause);
        this.eventId = eventId;
        this.listenerType = listenerType;
    }

    @Override
    public String toString() {
        return String.format("EventHandleException{eventId='%s', listenerType='%s', message='%s'}", 
                eventId, listenerType, getMessage());
    }
}