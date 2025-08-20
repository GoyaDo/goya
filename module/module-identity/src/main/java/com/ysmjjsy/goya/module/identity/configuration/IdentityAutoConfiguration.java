package com.ysmjjsy.goya.module.identity.configuration;

import com.ysmjjsy.goya.module.identity.configuration.properties.IdentityProperties;
import com.ysmjjsy.goya.module.identity.manage.*;
import com.ysmjjsy.goya.module.identity.stamp.LockedUserDetailsStampManager;
import com.ysmjjsy.goya.module.identity.stamp.SignInFailureLimitedStampManager;
import com.ysmjjsy.goya.module.identity.user.DefaultStrategyUserService;
import com.ysmjjsy.goya.module.identity.user.StrategyUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:33
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(IdentityProperties.class)
public class IdentityAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [identity] configure.");
    }


    @Bean
    @ConditionalOnMissingBean
    public UserStatusEventManager userStatusEventManager() {
        DefaultAccountStatusEventManager manager = new DefaultAccountStatusEventManager();
        log.trace("[Goya] |- module [identity] |- bean [userStatusEventManager] register.");
        return manager;
    }

    @Bean
    public LockedUserDetailsStampManager lockedUserDetailsStampManager(IdentityProperties identityProperties) {
        LockedUserDetailsStampManager manager = new LockedUserDetailsStampManager(identityProperties);
        log.trace("[Goya] |- module [identity] |- bean [lockedUserDetailsStampManager] register.");
        return manager;
    }

    @Bean
    public SignInFailureLimitedStampManager signInFailureLimitedStampManager(IdentityProperties identityProperties) {
        SignInFailureLimitedStampManager manager = new SignInFailureLimitedStampManager(identityProperties);
        log.trace("[Goya] |- module [identity] |- bean [signInFailureLimitedStampManager] register.");
        return manager;
    }

    @Bean
    @ConditionalOnMissingBean
    public UserStatusManager userStatusManager(
            StrategyUserService strategyUserService,
            UserStatusEventManager userStatusEventManager,
            LockedUserDetailsStampManager lockedUserDetailsStampManager,
            SignInFailureLimitedStampManager signInFailureLimitedStampManager
    ) {
        UserStatusManager manager = new UserStatusManager(
                strategyUserService,
                userStatusEventManager,
                lockedUserDetailsStampManager,
                signInFailureLimitedStampManager
        );
        log.trace("[Goya] |- module [identity] |- bean [userStatusManager] register.");
        return manager;
    }

    @Bean
    @ConditionalOnBean(RedisMessageListenerContainer.class)
    public UserAutoUnLockListener userAutoUnLockListener(RedisMessageListenerContainer listenerContainer,
                                                         UserStatusManager userStatusManager) {
        UserAutoUnLockListener listener = new UserAutoUnLockListener(listenerContainer,userStatusManager);
        log.trace("[Goya] |- module [identity] |- bean [userAutoUnLockListener] register.");
        return listener;
    }

    @Bean
    public UserReleaseFromCacheListener accountReleaseFromCacheListener(UserStatusManager userStatusManager) {
        UserReleaseFromCacheListener listener = new UserReleaseFromCacheListener(userStatusManager);
        log.trace("[Goya] |- module [identity] |- bean [accountReleaseFromCacheListener] register.");
        return listener;
    }

    @Bean
    @ConditionalOnMissingBean
    @Order
    public StrategyUserService strategyUserService() {
        DefaultStrategyUserService userService = new DefaultStrategyUserService();
        log.trace("[Goya] |- module [identity] |- bean [strategyUserService] register.");
        return userService;
    }
}
