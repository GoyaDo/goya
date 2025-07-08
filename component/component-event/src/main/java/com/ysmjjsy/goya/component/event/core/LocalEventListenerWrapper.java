package com.ysmjjsy.goya.component.event.core;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 本地事件监听器包装器
 * 封装监听器的元信息和匹配逻辑，支持topic过滤、SpEL条件表达式等
 *
 * @author goya
 * @since 2025/6/21 10:00
 */
@Getter
public class LocalEventListenerWrapper {

    private static final Logger logger = LoggerFactory.getLogger(LocalEventListenerWrapper.class);
    private static final SpelExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final GoyaEventListener<?> listener;
    private final Class<? extends GoyaEvent> eventType;
    private final Set<String> topics; // 监听的topic集合，空集合表示监听所有topic
    private final String condition; // SpEL条件表达式
    private final Expression conditionExpression; // 预编译的SpEL表达式
    private final int priority;
    private final boolean async;
    private final String listenerName; // 监听器标识名称

    public LocalEventListenerWrapper(GoyaEventListener<?> listener,
                                    Class<? extends GoyaEvent> eventType,
                                    Set<String> topics,
                                    String condition,
                                    int priority,
                                    boolean async,
                                    String listenerName) {
        this.listener = listener;
        this.eventType = eventType;
        this.topics = topics != null ? new HashSet<>(topics) : Collections.emptySet();
        this.condition = condition;
        this.priority = priority;
        this.async = async;
        this.listenerName = listenerName != null ? listenerName : listener.getClass().getSimpleName();

        // 预编译SpEL表达式
        if (StringUtils.hasText(condition)) {
            try {
                this.conditionExpression = EXPRESSION_PARSER.parseExpression(condition);
            } catch (Exception e) {
                logger.warn("Failed to parse SpEL condition '{}' for listener {}: {}", 
                           condition, this.listenerName, e.getMessage());
                throw new IllegalArgumentException("Invalid SpEL condition: " + condition, e);
            }
        } else {
            this.conditionExpression = null;
        }
    }

    /**
     * 判断是否应该处理某个事件
     *
     * @param event 事件对象
     * @param eventTopic 事件的topic
     * @return 是否应该处理
     */
    public boolean shouldHandle(GoyaEvent event, String eventTopic) {
        // 1. 检查事件类型匹配
        if (!eventType.isAssignableFrom(event.getClass())) {
            logger.trace("Event type mismatch for listener {}: expected {}, got {}", 
                        listenerName, eventType.getSimpleName(), event.getClass().getSimpleName());
            return false;
        }

        // 2. 检查topic匹配
        if (!shouldHandleTopic(eventTopic)) {
            logger.trace("Topic mismatch for listener {}: listener topics {}, event topic {}", 
                        listenerName, topics, eventTopic);
            return false;
        }

        // 3. 检查条件表达式
        if (!evaluateCondition(event)) {
            logger.trace("Condition mismatch for listener {}: condition '{}', event {}", 
                        listenerName, condition, event.getEventId());
            return false;
        }

        logger.debug("Listener {} matches event {}: eventType={}, topic={}", 
                    listenerName, event.getEventId(), eventType.getSimpleName(), eventTopic);
        return true;
    }

    /**
     * 检查topic是否匹配
     *
     * @param eventTopic 事件的topic
     * @return 是否匹配
     */
    private boolean shouldHandleTopic(String eventTopic) {
        // 如果监听器没有指定topics，则监听所有topic（向后兼容）
        if (topics.isEmpty()) {
            return true;
        }

        // 如果事件没有指定topic，使用空字符串进行匹配
        String topicToMatch = eventTopic != null ? eventTopic : "";
        
        // 检查是否在监听器的topic列表中
        return topics.contains(topicToMatch);
    }

    /**
     * 评估SpEL条件表达式
     *
     * @param event 事件对象
     * @return 是否满足条件
     */
    private boolean evaluateCondition(GoyaEvent event) {
        if (conditionExpression == null) {
            return true;
        }

        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("event", event);
            context.setVariable("headers", event.getHeaders());
            context.setVariable("eventType", event.getEventType());
            context.setVariable("eventId", event.getEventId());
            context.setVariable("topic", event.getTopic());

            Object result = conditionExpression.getValue(context);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            logger.warn("Error evaluating condition expression '{}' for event {}: {}", 
                       condition, event.getEventId(), e.getMessage());
            return false;
        }
    }

    /**
     * 执行监听器
     *
     * @param event 事件对象
     * @throws EventHandleException 处理异常
     */
    @SuppressWarnings("unchecked")
    public void handle(GoyaEvent event) throws EventHandleException {
        try {
            GoyaEventListener<GoyaEvent> typedListener = (GoyaEventListener<GoyaEvent>) listener;
            typedListener.onEvent(event);
        } catch (EventHandleException e) {
            throw e;
        } catch (Exception e) {
            throw new EventHandleException(
                event.getEventId(),
                listenerName,
                "Failed to handle event in listener",
                e
            );
        }
    }

    @Override
    public String toString() {
        return String.format("LocalEventListenerWrapper{name='%s', eventType=%s, topics=%s, priority=%d, async=%s}", 
                           listenerName, eventType.getSimpleName(), topics, priority, async);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LocalEventListenerWrapper other = (LocalEventListenerWrapper) obj;
        return listener.equals(other.listener) && eventType.equals(other.eventType);
    }

    @Override
    public int hashCode() {
        return listener.hashCode() ^ eventType.hashCode();
    }
} 