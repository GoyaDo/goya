package com.ysmjjsy.goya.module.identity.manage;

import com.ysmjjsy.goya.component.common.constants.SymbolConstants;
import com.ysmjjsy.goya.module.identity.constants.IdentityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * <p>账户锁定状态监听</p>
 *
 * @author goya
 * @since 2025/8/19 15:19
 */
@Slf4j
public class UserAutoUnLockListener extends KeyExpirationEventMessageListener {

    private final UserStatusManager userStatusManager;

    public UserAutoUnLockListener(RedisMessageListenerContainer listenerContainer,UserStatusManager userStatusManager) {
        super(listenerContainer);
        this.userStatusManager = userStatusManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (StringUtils.contains(key, IdentityConstants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL)) {
            String userId = StringUtils.substringAfterLast(key, SymbolConstants.COLON);
            log.info("[Goya] |- Parse the user [{}] at expired redis cache key [{}]", userId, key);
            if (StringUtils.isNotBlank(userId)) {
                log.debug("[Goya] |- Automatically unlock user account [{}]", userId);
                userStatusManager.unlock(userId);
            }
        }
    }
}
