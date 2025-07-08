package com.ysmjjsy.goya.component.event.configuration;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import com.ysmjjsy.goya.component.event.core.*;
import com.ysmjjsy.goya.component.event.serializer.EventSerializer;
import com.ysmjjsy.goya.component.event.serializer.JacksonEventSerializer;
import com.ysmjjsy.goya.component.event.transport.EventTransport;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * 事件配置类
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(EventBusProperties.class)
@ConditionalOnProperty(prefix = "goya.event", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableAsync
public class EventConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [event] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public EventSerializer eventSerializer() {
        return new JacksonEventSerializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public EventRouter eventRouter(EventBusProperties properties) {
        String defaultTopic = resolveDefaultTopic(properties);
        log.info("Event bus using default topic: {}", defaultTopic);
        return new DefaultEventRouter(defaultTopic);
    }

    @Bean
    @ConditionalOnMissingBean
    public GoyaEventBus eventBus(ApplicationEventPublisher applicationEventPublisher,
                                 List<EventTransport> eventTransports,
                                 EventRouter eventRouter,
                                 MeterRegistry meterRegistry) {
        log.info("Creating EventBus with {} transports", eventTransports.size());
        return new DefaultEventBus(applicationEventPublisher, eventTransports, eventRouter, meterRegistry);
    }

    /**
     * 智能事件监听器自动注册器
     */
    @Bean
    @ConditionalOnProperty(prefix = "goya.event.auto-registration", name = "annotation-enabled", havingValue = "true", matchIfMissing = true)
    public EventListenerAutoRegistrar eventListenerAutoRegistrar() {
        log.info("Enabling smart event listener auto-registration using SmartInitializingSingleton");
        return new EventListenerAutoRegistrar();
    }

    @Bean(name = "eventBusTaskExecutor")
    @ConditionalOnProperty(prefix = "goya.event.async", name = "enabled", havingValue = "true", matchIfMissing = true)
    public Executor eventBusTaskExecutor(EventBusProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        EventBusProperties.Async asyncConfig = properties.getAsync();

        executor.setCorePoolSize(asyncConfig.getCorePoolSize());
        executor.setMaxPoolSize(asyncConfig.getMaxPoolSize());
        executor.setQueueCapacity(asyncConfig.getQueueCapacity());
        executor.setThreadNamePrefix(asyncConfig.getThreadNamePrefix());
        executor.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        log.info("Created async task executor for event bus with core={}, max={}, queue={}",
                asyncConfig.getCorePoolSize(), asyncConfig.getMaxPoolSize(), asyncConfig.getQueueCapacity());

        return executor;
    }

    /**
     * 解析默认主题
     * 如果配置了defaultTopic则使用配置的，否则使用applicationName
     */
    private String resolveDefaultTopic(EventBusProperties properties) {
        if (StringUtils.isNotBlank(properties.getDefaultTopic())) {
            return properties.getDefaultTopic().trim();
        }
        
        try {
            String applicationName = GoyaContextHolder.getInstance().getApplicationName();
            if (StringUtils.isNotBlank(applicationName)) {
                return applicationName;
            }
        } catch (Exception e) {
            log.warn("Failed to get application name: {}", e.getMessage());
        }
        
        return "goya-events"; // 兜底默认值
    }

    /**
     * 健康检查配置
     */
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(name = "org.springframework.boot.actuator.health.HealthIndicator")
    @ConditionalOnProperty(prefix = "goya.event", name = "health-enabled", havingValue = "true", matchIfMissing = true)
    static class HealthConfiguration {

        @Bean
        public EventBusHealthIndicator eventBusHealthIndicator(GoyaEventBus eventBus) {
            return new EventBusHealthIndicator(eventBus);
        }
    }
}
