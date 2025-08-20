package com.ysmjjsy.goya.module.identity.manage;

import com.ysmjjsy.goya.component.bus.event.strategy.ApplicationStrategyEventManager;
import com.ysmjjsy.goya.module.identity.event.ChangeUserStatusEvent;

/**
 * <p>用户状态变更服务</p>
 *
 * @author goya
 * @since 2025/8/19 14:28
 */
public interface UserStatusEventManager extends ApplicationStrategyEventManager<ChangeUserStatusEvent> {

}
