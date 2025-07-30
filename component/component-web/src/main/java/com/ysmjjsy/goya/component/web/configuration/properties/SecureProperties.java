package com.ysmjjsy.goya.component.web.configuration.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;
import java.time.Duration;

/**
 * <p>Description: 跟踪标记配置属性 </p>
 *
 * @author goya
 * @since 2021/8/24 15:53
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_PREFIX_SECURE)
public class SecureProperties {

    private Idempotent idempotent = new Idempotent();
    private AccessLimited accessLimited = new AccessLimited();

    @Setter
    @Getter
    public static class Idempotent implements Serializable {

        @Serial
        private static final long serialVersionUID = 5514951879141004193L;
        /**
         * 幂等签章缓存默认过期时间，以防Token删除失败后，缓存数据始终存在影响使用，默认：5秒
         */
        private Duration expire = Duration.ofSeconds(5);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("expire", expire)
                    .toString();
        }
    }

    @Setter
    @Getter
    public static class AccessLimited implements Serializable {

        @Serial
        private static final long serialVersionUID = -4072367924338613714L;
        /**
         * 单位时间内同一个接口可以访问的次数，默认10次
         */
        private int maxTimes = 10;

        /**
         * 持续时间，即在多长时间内，限制访问多少次。默认为 30秒。
         */
        private Duration expire = Duration.ofSeconds(30);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("maxTimes", maxTimes)
                    .add("expire", expire)
                    .toString();
        }
    }
}
