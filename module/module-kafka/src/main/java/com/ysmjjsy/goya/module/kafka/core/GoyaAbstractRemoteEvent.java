package com.ysmjjsy.goya.module.kafka.core;

import com.ysmjjsy.goya.component.bus.enums.EventStatus;
import com.ysmjjsy.goya.component.bus.event.domain.GoyaEvent;
import com.ysmjjsy.goya.component.distributedid.utils.SnowflakeIdUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/8 10:34
 */
@Getter
public abstract class GoyaAbstractRemoteEvent extends RemoteApplicationEvent implements GoyaEvent {

    @Serial
    private static final long serialVersionUID = 1534639056094107619L;

    /**
     * 事件ID - 全局唯一标识符
     */
    protected final String eventId;

    /**
     * 事件索引
     */
    protected String index;

    /**
     * 事件创建时间
     */
    protected final LocalDateTime createdTime = LocalDateTime.now();

    /**
     * 事件状态
     */
    @Setter
    protected EventStatus eventStatus;

    protected GoyaAbstractRemoteEvent(Object source, String originService, Destination destination) {
        super(source, originService, destination);
        this.eventId = "EVENT-"+ SnowflakeIdUtil.nextIdStr();
        this.eventStatus = EventStatus.PENDING;
    }
}
