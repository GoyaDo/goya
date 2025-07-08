package com.ysmjjsy.goya.component.event.core;

import com.ysmjjsy.goya.component.event.transport.EventTransport;
import com.ysmjjsy.goya.component.event.transport.TransportHealth;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 默认事件总线实现
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class DefaultEventBus implements GoyaEventBus {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventBus.class);

    private final ApplicationEventPublisher applicationEventPublisher;
    private final List<EventTransport> eventTransports;
    private final EventRouter eventRouter;
    private final Map<Class<? extends GoyaEvent>, List<LocalEventListenerWrapper>> localListeners;

    // 监控指标
    private final Counter publishedEventsCounter;
    private final Counter failedEventsCounter;
    private final Timer publishLatency;

    public DefaultEventBus(ApplicationEventPublisher applicationEventPublisher,
                           List<EventTransport> eventTransports,
                           EventRouter eventRouter,
                           MeterRegistry meterRegistry) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.eventTransports = eventTransports != null ? eventTransports : Collections.emptyList();
        this.eventRouter = eventRouter;
        this.localListeners = new ConcurrentHashMap<>();

        // 初始化监控指标
        if (meterRegistry != null) {
            this.publishedEventsCounter = Counter.builder("eventbus.events.published")
                    .description("Number of published events")
                    .register(meterRegistry);
            this.failedEventsCounter = Counter.builder("eventbus.events.failed")
                    .description("Number of failed events")
                    .register(meterRegistry);
            this.publishLatency = Timer.builder("eventbus.publish.latency")
                    .description("Event publish latency")
                    .register(meterRegistry);
        } else {
            this.publishedEventsCounter = null;
            this.failedEventsCounter = null;
            this.publishLatency = null;
        }
    }

    @Override
    public EventPublishResult publish(GoyaEvent event) {
        Timer.Sample sample = publishLatency != null ? Timer.start() : null;

        try {
            logger.debug("Publishing event: {}", event);

            EventRoutingDecision decision = eventRouter.route(event);
            EventPublishResult.Builder resultBuilder = EventPublishResult.builder()
                    .eventId(event.getEventId());

            int localCount = 0;
            int remoteCount = 0;
            List<String> errors = new ArrayList<>();

            // 本地处理
            if (decision.shouldPublishLocal()) {
                try {
                    applicationEventPublisher.publishEvent(event);
                    // 获取用于本地处理的topic
                    String localTopic = determineLocalTopic(event, decision);
                    localCount = processLocalListeners(event, localTopic);
                    logger.debug("Event {} published locally to {} listeners with topic {}", 
                                event.getEventId(), localCount, localTopic);
                } catch (Exception e) {
                    String error = "Local publish failed: " + e.getMessage();
                    errors.add(error);
                    logger.error("Failed to publish event {} locally", event.getEventId(), e);
                }
            }

            // 远程广播
            if (decision.shouldPublishRemote()) {
                for (String topic : decision.getRemoteTopics()) {
                    for (EventTransport transport : eventTransports) {
                        try {
                            // 同步等待结果
                            transport.send(event, topic).get();
                            remoteCount++;
                        } catch (Exception e) {
                            String error = "Remote publish failed on " + transport.getTransportType() + ": " + e.getMessage();
                            errors.add(error);
                            logger.error("Failed to send event {} to {} on topic {}",
                                    event.getEventId(), transport.getTransportType(), topic, e);
                        }
                    }
                }
            }

            boolean success = errors.isEmpty();
            EventPublishResult result = resultBuilder
                    .success(success)
                    .localListeners(localCount)
                    .remoteTargets(remoteCount)
                    .build();

            errors.forEach(resultBuilder::addError);

            if (publishedEventsCounter != null) {
                publishedEventsCounter.increment();
            }
            if (!success && failedEventsCounter != null) {
                failedEventsCounter.increment();
            }

            return result;

        } finally {
            if (sample != null && publishLatency != null) {
                sample.stop(publishLatency);
            }
        }
    }

    @Override
    @Async
    public CompletableFuture<EventPublishResult> publishAsync(GoyaEvent event) {
        return CompletableFuture.supplyAsync(() -> publish(event));
    }

    @Override
    public EventPublishResult publishTransactional(GoyaEvent event) {
        // 在事务提交后发布远程事件
        TransactionalEventHandler handler = new TransactionalEventHandler(event);
        applicationEventPublisher.publishEvent(handler);

        // 立即处理本地事件
        return publish(event);
    }

    @Override
    public <T extends GoyaEvent> void subscribe(GoyaEventListener<T> listener, Class<T> eventType) {
        // 创建一个监听所有topic的wrapper（向后兼容）
        LocalEventListenerWrapper wrapper = new LocalEventListenerWrapper(
            listener, eventType, Collections.emptySet(), null, 0, false, 
            listener.getClass().getSimpleName()
        );
        subscribe(wrapper, eventType);
    }

    /**
     * 注册本地监听器包装器
     * 
     * @param wrapper 监听器包装器
     * @param eventType 事件类型
     */
    @Override
    public <T extends GoyaEvent> void subscribe(LocalEventListenerWrapper wrapper, Class<T> eventType) {
        localListeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                .add(wrapper);
        logger.info("Registered local listener wrapper for event type {}: {}", 
                   eventType.getSimpleName(), wrapper);
    }

    @Override
    public <T extends GoyaEvent> void unsubscribe(GoyaEventListener<T> listener, Class<T> eventType) {
        List<LocalEventListenerWrapper> wrappers = localListeners.get(eventType);
        if (wrappers != null) {
            wrappers.removeIf(wrapper -> wrapper.getListener().equals(listener));
            if (wrappers.isEmpty()) {
                localListeners.remove(eventType);
            }
        }
        logger.info("Unregistered local listener for event type: {}", eventType.getSimpleName());
    }

    @Override
    public void shutdown() {
        eventTransports.forEach(EventTransport::stop);
        localListeners.clear();
        logger.info("Event bus shutdown completed");
    }

    @Override
    public EventBusHealth getHealth() {
        Map<String, Object> details = new HashMap<>();
        boolean allHealthy = true;

        // 检查传输组件健康状态
        for (EventTransport transport : eventTransports) {
            try {
                var health = transport.getHealth();
                details.put(transport.getTransportType(), health.getStatus());
                if (health.getStatus() != TransportHealth.Status.UP) {
                    allHealthy = false;
                }
            } catch (Exception e) {
                details.put(transport.getTransportType(), "ERROR: " + e.getMessage());
                allHealthy = false;
            }
        }

        details.put("localListeners", localListeners.size());
        details.put("transports", eventTransports.size());

        if (allHealthy) {
            return EventBusHealth.up();
        } else {
            return EventBusHealth.degraded("Some transports are unhealthy", details);
        }
    }

    private int processLocalListeners(GoyaEvent event, String eventTopic) {
        List<LocalEventListenerWrapper> wrappers = localListeners.get(event.getClass());
        if (wrappers == null || wrappers.isEmpty()) {
            return 0;
        }

        // 过滤出应该处理这个事件的监听器并按优先级排序
        List<LocalEventListenerWrapper> matchedWrappers = wrappers.stream()
            .filter(wrapper -> wrapper.shouldHandle(event, eventTopic))
            .sorted(Comparator.comparingInt(LocalEventListenerWrapper::getPriority))
            .toList();

        logger.debug("Found {} matched listeners for event {} with topic {}", 
                    matchedWrappers.size(), event.getEventId(), eventTopic);

        int count = 0;
        for (LocalEventListenerWrapper wrapper : matchedWrappers) {
            try {
                if (wrapper.isAsync()) {
                    processListenerAsync(wrapper, event);
                } else {
                    wrapper.handle(event);
                }
                count++;
                logger.trace("Successfully processed event {} in listener {}", 
                           event.getEventId(), wrapper.getListenerName());
            } catch (Exception e) {
                logger.error("Error processing event {} in listener {}: {}", 
                           event.getEventId(), wrapper.getListenerName(), e.getMessage(), e);
                if (failedEventsCounter != null) {
                    failedEventsCounter.increment();
                }
            }
        }
        return count;
    }

    @Async
    public void processListenerAsync(LocalEventListenerWrapper wrapper, GoyaEvent event) {
        try {
            wrapper.handle(event);
            logger.trace("Successfully processed async event {} in listener {}", 
                       event.getEventId(), wrapper.getListenerName());
        } catch (Exception e) {
            logger.error("Error in async event processing for event {} in listener {}: {}", 
                       event.getEventId(), wrapper.getListenerName(), e.getMessage(), e);
            if (failedEventsCounter != null) {
                failedEventsCounter.increment();
            }
        }
    }

    /**
     * 确定用于本地处理的topic
     * 
     * @param event 事件对象
     * @param decision 路由决策
     * @return 用于本地处理的topic
     */
    private String determineLocalTopic(GoyaEvent event, EventRoutingDecision decision) {
        // 优先使用远程topic（如果有），确保本地和远程的一致性
        if (!decision.getRemoteTopics().isEmpty()) {
            return decision.getRemoteTopics().get(0);
        }
        
        // 如果没有远程topic，使用事件的默认topic
        return event.getTopic();
    }

    /**
     * 事务事件处理器
     */
    private class TransactionalEventHandler {
        private final GoyaEvent event;

        public TransactionalEventHandler(GoyaEvent event) {
            this.event = event;
        }

        @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
        public void handleAfterCommit() {
            EventRoutingDecision decision = eventRouter.route(event);
            if (decision.shouldPublishRemote()) {
                for (String topic : decision.getRemoteTopics()) {
                    for (EventTransport transport : eventTransports) {
                        transport.send(event, topic)
                                .whenComplete((result, throwable) -> {
                                    if (throwable != null) {
                                        logger.error("Failed to send transactional event {} to {} on topic {}",
                                                event.getEventId(), transport.getTransportType(), topic, throwable);
                                    }
                                });
                    }
                }
            }
        }
    }
}