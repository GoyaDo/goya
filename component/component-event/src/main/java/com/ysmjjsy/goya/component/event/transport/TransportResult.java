package com.ysmjjsy.goya.component.event.transport;

import java.time.LocalDateTime;

/**
 * 传输结果
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class TransportResult {
    
    private final boolean success;
    private final String message;
    private final String transportType;
    private final String topic;
    private final String eventId;
    private final LocalDateTime sendTime;
    private final Throwable error;

    private TransportResult(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.transportType = builder.transportType;
        this.topic = builder.topic;
        this.eventId = builder.eventId;
        this.sendTime = builder.sendTime;
        this.error = builder.error;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static TransportResult success(String transportType, String topic, String eventId) {
        return builder()
                .success(true)
                .transportType(transportType)
                .topic(topic)
                .eventId(eventId)
                .sendTime(LocalDateTime.now())
                .message("Event sent successfully")
                .build();
    }

    public static TransportResult failure(String transportType, String topic, String eventId, Throwable error) {
        return builder()
                .success(false)
                .transportType(transportType)
                .topic(topic)
                .eventId(eventId)
                .sendTime(LocalDateTime.now())
                .error(error)
                .message("Failed to send event: " + error.getMessage())
                .build();
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTransportType() {
        return transportType;
    }

    public String getTopic() {
        return topic;
    }

    public String getEventId() {
        return eventId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public Throwable getError() {
        return error;
    }

    public static class Builder {
        private boolean success;
        private String message;
        private String transportType;
        private String topic;
        private String eventId;
        private LocalDateTime sendTime;
        private Throwable error;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder transportType(String transportType) {
            this.transportType = transportType;
            return this;
        }

        public Builder topic(String topic) {
            this.topic = topic;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder sendTime(LocalDateTime sendTime) {
            this.sendTime = sendTime;
            return this;
        }

        public Builder error(Throwable error) {
            this.error = error;
            return this;
        }

        public TransportResult build() {
            return new TransportResult(this);
        }
    }

    @Override
    public String toString() {
        return String.format("TransportResult{success=%s, transportType='%s', topic='%s', eventId='%s', sendTime=%s, message='%s'}", 
                success, transportType, topic, eventId, sendTime, message);
    }
}