package com.ysmjjsy.goya.module.justauth.properties;

import com.ysmjjsy.goya.module.justauth.constants.JustAuthConstants;
import lombok.Getter;
import lombok.Setter;
import me.zhyd.oauth.config.AuthConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

/**
 * <p>Description: 用于支持JustAuth第三方登录的配置 </p>
 *
 * @author goya
 * @since 2021/5/16 10:24
 */
@Setter
@Getter
@ConfigurationProperties(prefix = JustAuthConstants.PROPERTY_ACCESS_JUSTAUTH)
public class JustAuthProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * State 缓存时长，默认5分钟
     */
    private Duration timeout = Duration.ofMinutes(5);
    /**
     * 第三方系统登录配置信息
     */
    private Map<String, AuthConfig> configs;

}
