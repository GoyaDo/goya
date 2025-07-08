package com.ysmjjsy.goya.component.event.transport;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 传输组件健康状态
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class TransportHealth {
    
    public enum Status {
        UP, DOWN, DEGRADED, UNKNOWN
    }

    private final Status status;
    private final String transportType;
    private final LocalDateTime checkTime;
    private final String message;
    private final Map<String, Object> details;

    public TransportHealth(Status status, String transportType, String message, Map<String, Object> details) {
        this.status = status;
        this.transportType = transportType;
        this.message = message;
        this.details = details;
        this.checkTime = LocalDateTime.now();
    }

    public static TransportHealth up(String transportType) {
        return new TransportHealth(Status.UP, transportType, "Transport is healthy", Map.of());
    }

    public static TransportHealth down(String transportType, String reason) {
        return new TransportHealth(Status.DOWN, transportType, reason, Map.of());
    }

    public static TransportHealth degraded(String transportType, String reason, Map<String, Object> details) {
        return new TransportHealth(Status.DEGRADED, transportType, reason, details);
    }

    // Getters
    public Status getStatus() {
        return status;
    }

    public String getTransportType() {
        return transportType;
    }

    public LocalDateTime getCheckTime() {
        return checkTime;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("TransportHealth{status=%s, transportType='%s', message='%s', checkTime=%s}", 
                status, transportType, message, checkTime);
    }
}