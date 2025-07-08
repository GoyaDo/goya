package com.ysmjjsy.goya.component.event.core;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * 事件路由决策
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class EventRoutingDecision {
    
    private final boolean publishLocal;
    private final boolean publishRemote;
    @Getter
    private final List<String> remoteTopics;
    @Getter
    private final EventRoutingStrategy strategy;

    public EventRoutingDecision(boolean publishLocal, boolean publishRemote, 
                               List<String> remoteTopics, EventRoutingStrategy strategy) {
        this.publishLocal = publishLocal;
        this.publishRemote = publishRemote;
        this.remoteTopics = remoteTopics != null ? remoteTopics : Collections.emptyList();
        this.strategy = strategy;
    }

    public static EventRoutingDecision localOnly() {
        return new EventRoutingDecision(true, false, Collections.emptyList(), EventRoutingStrategy.LOCAL_ONLY);
    }

    public static EventRoutingDecision remoteOnly(List<String> topics) {
        return new EventRoutingDecision(false, true, topics, EventRoutingStrategy.REMOTE_ONLY);
    }

    public static EventRoutingDecision localAndRemote(List<String> topics) {
        return new EventRoutingDecision(true, true, topics, EventRoutingStrategy.LOCAL_AND_REMOTE);
    }

    public static EventRoutingDecision remoteFirst(List<String> topics) {
        return new EventRoutingDecision(true, true, topics, EventRoutingStrategy.REMOTE_FIRST);
    }

    // Getters
    public boolean shouldPublishLocal() {
        return publishLocal;
    }

    public boolean shouldPublishRemote() {
        return publishRemote;
    }

    @Override
    public String toString() {
        return String.format("EventRoutingDecision{local=%s, remote=%s, topics=%s, strategy=%s}", 
                publishLocal, publishRemote, remoteTopics, strategy);
    }
}