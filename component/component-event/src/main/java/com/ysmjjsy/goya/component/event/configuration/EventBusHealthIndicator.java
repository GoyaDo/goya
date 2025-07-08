package com.ysmjjsy.goya.component.event.configuration;

import com.ysmjjsy.goya.component.event.core.GoyaEventBus;
import com.ysmjjsy.goya.component.event.core.EventBusHealth;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * 事件总线健康检查指示器
 *
 * @author goya
 * @since 2025/6/13 17:56
 */
public class EventBusHealthIndicator implements HealthIndicator {

    private final GoyaEventBus eventBus;

    public EventBusHealthIndicator(GoyaEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Health health() {
        try {
            EventBusHealth eventBusHealth = eventBus.getHealth();
            
            Health.Builder builder = switch (eventBusHealth.getStatus()) {
                case UP -> Health.up();
                case DOWN -> Health.down();
                case DEGRADED -> Health.status("DEGRADED");
            };

            return builder
                    .withDetail("message", eventBusHealth.getMessage())
                    .withDetail("checkTime", eventBusHealth.getCheckTime())
                    .withDetails(eventBusHealth.getDetails())
                    .build();
                    
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}