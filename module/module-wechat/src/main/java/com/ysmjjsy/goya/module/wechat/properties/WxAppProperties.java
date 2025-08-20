package com.ysmjjsy.goya.module.wechat.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.module.wechat.constants.WeChatConstants;
import com.ysmjjsy.goya.module.wechat.enums.MiniProgramState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 微信小程序配置属性 </p>
 *
 * @author goya
 * @since 2021/3/26 17:27
 */
@Setter
@Getter
@ConfigurationProperties(prefix = WeChatConstants.PROPERTY_ACCESS_WXAPP)
public class WxAppProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 341175143633304593L;

    /**
     * 是否开启
     */
    private Boolean enabled;
    /**
     * 默认App Id
     */
    private String defaultAppId;

    /**
     * 小程序配置列表
     */
    private List<Config> configs;

    /**
     * 小程序订阅消息配置列表
     */
    private List<Subscribe> subscribes;

    @Setter
    @Getter
    public static class Config {
        /**
         * 设置微信小程序的appid
         */
        private String appId;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON
         */
        private String messageDataFormat;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("appid", appId)
                    .add("secret", secret)
                    .add("token", token)
                    .add("aesKey", aesKey)
                    .add("messageDataFormat", messageDataFormat)
                    .toString();
        }
    }

    @Setter
    @Getter
    public static class Subscribe {

        /**
         * 订阅消息指定的小程序跳转页面地址
         */
        private String redirectPage;
        /**
         * 订阅消息模版ID
         */
        private String templateId;

        /**
         * 自定义Message区分ID，用于获取不同的SubscribeMessageHandler
         */
        private String subscribeId;

        private MiniProgramState miniProgramState = MiniProgramState.formal;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("redirectPage", redirectPage)
                    .add("templateId", templateId)
                    .add("subscribeId", subscribeId)
                    .add("miniProgramState", miniProgramState)
                    .toString();
        }
    }
}
