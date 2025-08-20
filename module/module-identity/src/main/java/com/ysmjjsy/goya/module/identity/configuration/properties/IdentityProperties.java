package com.ysmjjsy.goya.module.identity.configuration.properties;

import com.ysmjjsy.goya.module.identity.constants.IdentityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/19 14:43
 */
@Data
@ConfigurationProperties(prefix = IdentityConstants.PROPERTY_PREFIX_IDENTITY)
public class IdentityProperties {

    /**
     * 开启登录失败限制
     */
    private SignInFailureLimited signInFailureLimited = new SignInFailureLimited();

    /**
     * 同一终端登录限制
     */
    private SignInEndpointLimited signInEndpointLimited = new SignInEndpointLimited();

    /**
     * 账户踢出限制
     */
    private SignInKickOutLimited signInKickOutLimited = new SignInKickOutLimited();

    @Data
    public static class SignInFailureLimited {
        /**
         * 是否开启登录失败检测，默认开启
         */
        private Boolean enabled = true;

        /**
         * 允许允许最大失败次数
         */
        private Integer maxTimes = 5;

        /**
         * 是否自动解锁被锁定用户，默认开启
         */
        private Boolean autoUnlock = true;

        /**
         * 记录失败次数的缓存过期时间，默认：2小时。
         */
        private Duration expire = Duration.ofHours(2);
    }

    @Data
    public static class SignInEndpointLimited {
        /**
         * 同一终端登录限制是否开启，默认开启。
         */
        private Boolean enabled = false;

        /**
         * 统一终端，允许同时登录的最大数量
         */
        private Integer maximum = 1;
    }

    @Data
    public static class SignInKickOutLimited {
        /**
         * 是否开启 Session 踢出功能，默认开启
         */
        private Boolean enabled = true;
    }
}
