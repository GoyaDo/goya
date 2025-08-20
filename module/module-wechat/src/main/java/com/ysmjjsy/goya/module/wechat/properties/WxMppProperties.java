package com.ysmjjsy.goya.module.wechat.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.module.wechat.constants.WeChatConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * <p>Description: 微信公众号属性配置 </p>
 *
 * @author goya
 * @since 2021/4/7 13:17
 */
@Setter
@Getter
@ConfigurationProperties(prefix = WeChatConstants.PROPERTY_ACCESS_WXMPP)
public class WxMppProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * 是否使用redis存储access token
     */
    private boolean useRedis;

    /**
     * redis 配置
     */
    private RedisConfig redis;

    @Setter
    @Getter
    public static class RedisConfig {
        /**
         * redis服务器 主机地址
         */
        private String host;

        /**
         * redis服务器 端口号
         */
        private Integer port;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("host", host)
                    .add("port", port)
                    .toString();
        }
    }

    /**
     * 多个公众号配置信息
     */
    private List<MpConfig> configs;

    @Getter
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("appId", appId)
                    .add("secret", secret)
                    .add("token", token)
                    .add("aesKey", aesKey)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("useRedis", useRedis)
                .add("redis", redis)
                .add("configs", configs)
                .toString();
    }
}
