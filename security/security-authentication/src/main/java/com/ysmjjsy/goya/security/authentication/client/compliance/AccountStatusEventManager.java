package com.ysmjjsy.goya.security.authentication.client.compliance;

import com.ysmjjsy.goya.component.bus.event.strategy.ApplicationStrategyEventManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.ChangeUserStatusEvent;

/**
 * <p>Description: 用户状态变更服务 </p>
 *
 * @author goya
 * @since 2022/7/10 16:23
 */
public interface AccountStatusEventManager extends ApplicationStrategyEventManager<ChangeUserStatusEvent> {

}
