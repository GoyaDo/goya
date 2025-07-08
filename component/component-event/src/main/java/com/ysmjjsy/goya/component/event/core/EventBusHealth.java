package com.ysmjjsy.goya.component.event.core;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 事件总线健康状态
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class EventBusHealth {
    
    public enum Status {
        UP, DOWN, DEGRADED
    }

    private final Status status;
    private final LocalDateTime checkTime;
    private final Map<String, Object> details;
    private final String message;

    public EventBusHealth(Status status, String message, Map<String, Object> details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.checkTime = LocalDateTime.now();
    }

    public static EventBusHealth up() {
        return new EventBusHealth(Status.UP, "Event bus is healthy", Map.of());
    }

    public static EventBusHealth down(String reason) {
        return new EventBusHealth(Status.DOWN, reason, Map.of());
    }

    public static EventBusHealth degraded(String reason, Map<String, Object> details) {
        return new EventBusHealth(Status.DEGRADED, reason, details);
    }

    // Getters
    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("EventBusHealth{status=%s, message='%s', checkTime=%s}", 
                status, message, checkTime);
    }
}