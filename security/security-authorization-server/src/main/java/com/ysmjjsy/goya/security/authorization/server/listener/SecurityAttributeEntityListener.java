package com.ysmjjsy.goya.security.authorization.server.listener;

import com.ysmjjsy.goya.component.bus.event.domain.AbstractApplicationContextAware;
import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.event.SecurityAttributeChangeEvent;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/11 10:15
 */
@Slf4j
public class SecurityAttributeEntityListener extends AbstractApplicationContextAware {

    @PostUpdate
    protected void postUpdate(SecurityAttribute entity) {
        log.debug("[Goya] |- [1] SecurityAttribute entity @PostUpdate activated, value is : [{}]. Trigger SecurityAttribute change event.", entity.toString());
        publishEvent(new SecurityAttributeChangeEvent(this, entity));
    }
}
