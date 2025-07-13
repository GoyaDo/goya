package com.ysmjjsy.goya.component.bus.message.domain;

import com.ysmjjsy.goya.component.context.service.GoyaContextHolder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;

/**
 * <p>Description: WebSocket 模版消息 </p>
 *
 * @author goya
 * @since 2023/10/26 20:18
 */
@Data
public abstract class WebSocketMessage<T> implements Message<T> {

    @Serial
    private static final long serialVersionUID = -6862720457441546130L;

    private String id;

    private String userId;

    private T payload;

    private String destination;

    public String getId() {
        if (StringUtils.isNotBlank(id)) {
            return id;
        } else {
            return GoyaContextHolder.getInstance().getId();
        }
    }
}
