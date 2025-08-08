package com.ysmjjsy.goya.component.bus.core;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaEvent;
import com.ysmjjsy.goya.component.common.context.GoyaContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/8 09:51
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultGoyaEventBus extends AbstractGoyaEventBus{

    @Override
    public void publish(GoyaEvent event) {
        // 发布事件 TODO 暂时如此实现
        GoyaContextHolder.getInstance().publishEvent(event);
    }
}
