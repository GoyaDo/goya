package com.ysmjjsy.goya.component.bus.message.adapter;

import com.ysmjjsy.goya.component.bus.event.domain.GoyaAbstractEvent;
import com.ysmjjsy.goya.component.bus.message.domain.Message;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: 消息发送适配器 </p>
 * <p>
 * 各种类型消息发送组件，基于该接口实现各自的消息发送。
 *
 * @author goya
 * @since 2023/10/26 16:46
 */
public interface MessageSendingAdapter<P, D extends Message<P>, E extends GoyaAbstractEvent> extends ApplicationListener<E> {

}
