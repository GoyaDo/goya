package com.ysmjjsy.goya.security.authentication.client.compliance.event.listener;

import com.ysmjjsy.goya.component.pojo.constants.SymbolConstants;
import com.ysmjjsy.goya.security.authentication.client.compliance.SecurityAccountStatusManager;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.nio.charset.StandardCharsets;

/**
 * <p>Description: 账户锁定状态监听 </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
public class AccountAutoEnableListener extends KeyExpirationEventMessageListener {

    private static final Logger log = LoggerFactory.getLogger(AccountAutoEnableListener.class);

    private final SecurityAccountStatusManager accountStatusManager;

    public AccountAutoEnableListener(RedisMessageListenerContainer listenerContainer, SecurityAccountStatusManager accountStatusManager) {
        super(listenerContainer);
        this.accountStatusManager = accountStatusManager;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
        if (StringUtils.contains(key, SecurityConstants.CACHE_NAME_TOKEN_LOCKED_USER_DETAIL)) {
            String userId = StringUtils.substringAfterLast(key, SymbolConstants.COLON);
            log.info("[Goya] |- Parse the user [{}] at expired redis cache key [{}]", userId, key);
            if (StringUtils.isNotBlank(userId)) {
                log.debug("[Goya] |- Automatically unlock user account [{}]", userId);
                accountStatusManager.enable(userId);
            }
        }
    }
}
