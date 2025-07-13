package com.ysmjjsy.goya.security.authorization.server.listener;

import com.ysmjjsy.goya.security.authorization.server.domain.entity.SecurityAttribute;
import com.ysmjjsy.goya.security.authorization.server.event.SecurityAttributeChangeEvent;
import com.ysmjjsy.goya.security.authorization.server.processor.SecurityMetadataDistributeProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.ApplicationListener;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/13 21:10
 */
@Slf4j
@RequiredArgsConstructor
public class SecurityAttributeChangeListener implements ApplicationListener<SecurityAttributeChangeEvent> {

    private final SecurityMetadataDistributeProcessor securityMetadataDistributeProcessor;


    @Override
    public void onApplicationEvent(SecurityAttributeChangeEvent event) {
        SecurityAttribute sysAttribute = event.getData();
        if (ObjectUtils.isNotEmpty(sysAttribute)) {
            log.debug("[Goya] |- Got SysAttribute, start to process SysAttribute change.");
            securityMetadataDistributeProcessor.distributeChangedSecurityAttribute(sysAttribute);
        }
    }
}
