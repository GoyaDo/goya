package com.ysmjjsy.goya.security.authentication.client.compliance.event.listener;

import com.ysmjjsy.goya.security.authentication.client.compliance.SecurityAccountStatusManager;
import com.ysmjjsy.goya.security.authentication.client.compliance.event.AccountReleaseFromCacheEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;

/**
 * <p>Description: TODO </p>
 *
 * @author goya
 * @since 2022/7/8 21:27
 */
@RequiredArgsConstructor
public class AccountReleaseFromCacheListener implements ApplicationListener<AccountReleaseFromCacheEvent> {

    private final SecurityAccountStatusManager accountStatusManager;

    @Override
    public void onApplicationEvent(AccountReleaseFromCacheEvent event) {
        String username = event.getData();
        accountStatusManager.releaseFromCache(username);
    }
}
