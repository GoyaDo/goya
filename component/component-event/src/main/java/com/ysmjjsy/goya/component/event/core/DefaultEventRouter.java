package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 默认事件路由器实现
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Getter
public class DefaultEventRouter implements EventRouter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventRouter.class);

    /**
     * -- GETTER --
     *  获取默认主题
     */
    private final String defaultTopic;

    public DefaultEventRouter(String defaultTopic) {
        this.defaultTopic = defaultTopic != null ? defaultTopic : GoyaContextHolder.getInstance().getApplicationName();
    }

    @Override
    public EventRoutingDecision route(GoyaEvent event) {
        EventRoutingStrategy strategy = event.getRoutingStrategy();
        
        logger.debug("Routing event {} with strategy {}", event.getEventId(), strategy);
        return switch (strategy) {
            case LOCAL_ONLY -> EventRoutingDecision.localOnly();
            case REMOTE_ONLY -> EventRoutingDecision.remoteOnly(getTopicsForEvent(event));
            case LOCAL_AND_REMOTE -> EventRoutingDecision.localAndRemote(getTopicsForEvent(event));
            case REMOTE_FIRST -> EventRoutingDecision.remoteFirst(getTopicsForEvent(event));
        };
    }

    private List<String> getTopicsForEvent(GoyaEvent event) {
        List<String> topics = new ArrayList<>();
        
        // 1. 检查事件头中是否指定了自定义主题
        Object customTopic = event.getHeaders().get("topic");
        if (customTopic instanceof String && !((String) customTopic).trim().isEmpty()) {
            topics.add(((String) customTopic).trim());
            logger.debug("Using custom topic from event header: {}", customTopic);
            return topics;
        }

        // 2. 检查事件头中是否指定了多个主题
        Object customTopics = event.getHeaders().get("topics");
        if (customTopics instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> topicList = (List<String>) customTopics;
            for (String topic : topicList) {
                if (topic != null && !topic.trim().isEmpty()) {
                    topics.add(topic.trim());
                }
            }
            if (!topics.isEmpty()) {
                logger.debug("Using custom topics from event headers: {}", topics);
                return topics;
            }
        }

        return topics;
    }

}