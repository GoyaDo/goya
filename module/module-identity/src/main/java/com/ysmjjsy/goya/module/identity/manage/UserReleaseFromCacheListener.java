package com.ysmjjsy.goya.module.identity.manage;

import com.ysmjjsy.goya.module.identity.event.UserReleaseFromCacheEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
@RequiredArgsConstructor
public class UserReleaseFromCacheListener implements ApplicationListener<UserReleaseFromCacheEvent> {

    private final UserStatusManager userStatusManager;

    @Override
    public void onApplicationEvent(UserReleaseFromCacheEvent event) {
        String username = event.getData();
        userStatusManager.releaseFromCache(username);
    }
}
