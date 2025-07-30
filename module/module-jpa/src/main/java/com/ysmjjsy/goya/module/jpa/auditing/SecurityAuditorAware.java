package com.ysmjjsy.goya.module.jpa.auditing;

import com.ysmjjsy.goya.module.identity.context.GoyaLoginInfoContext;
import com.ysmjjsy.goya.module.identity.domain.GoyaUserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * <p>Description: 基于 Security 的数据库审计用户信息获取 </p>
 *
 * @author goya
 * @since 2024/4/8 16:01
 */
@Slf4j
public class SecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        GoyaUserPrincipal loginUser = GoyaLoginInfoContext.getLoginUser();
        return Optional.ofNullable(loginUser)
                .map(GoyaUserPrincipal::getId)
                .or(Optional::empty);
    }
}
