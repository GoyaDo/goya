package com.ysmjjsy.goya.component.event.core;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件发布结果
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class EventPublishResult {
    
    @Getter
    private final String eventId;
    @Getter
    private final boolean success;
    @Getter
    private final LocalDateTime publishTime;
    private final List<String> errors;
    @Getter
    private final int localListeners;
    @Getter
    private final int remoteTargets;

    private EventPublishResult(Builder builder) {
        this.eventId = builder.eventId;
        this.success = builder.success;
        this.publishTime = builder.publishTime;
        this.errors = new ArrayList<>(builder.errors);
        this.localListeners = builder.localListeners;
        this.remoteTargets = builder.remoteTargets;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static EventPublishResult success(String eventId) {
        return builder()
                .eventId(eventId)
                .success(true)
                .publishTime(LocalDateTime.now())
                .build();
    }

    public static EventPublishResult failure(String eventId, String error) {
        return builder()
                .eventId(eventId)
                .success(false)
                .publishTime(LocalDateTime.now())
                .addError(error)
                .build();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public static class Builder {
        private String eventId;
        private boolean success = false;
        private LocalDateTime publishTime = LocalDateTime.now();
        private List<String> errors = new ArrayList<>();
        private int localListeners = 0;
        private int remoteTargets = 0;

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder publishTime(LocalDateTime publishTime) {
            this.publishTime = publishTime;
            return this;
        }

        public Builder addError(String error) {
            this.errors.add(error);
            return this;
        }

        public Builder localListeners(int localListeners) {
            this.localListeners = localListeners;
            return this;
        }

        public Builder remoteTargets(int remoteTargets) {
            this.remoteTargets = remoteTargets;
            return this;
        }

        public EventPublishResult build() {
            return new EventPublishResult(this);
        }
    }

    @Override
    public String toString() {
        return String.format("EventPublishResult{eventId='%s', success=%s, publishTime=%s, localListeners=%d, remoteTargets=%d, errors=%s}",
                eventId, success, publishTime, localListeners, remoteTargets, errors);
    }
}