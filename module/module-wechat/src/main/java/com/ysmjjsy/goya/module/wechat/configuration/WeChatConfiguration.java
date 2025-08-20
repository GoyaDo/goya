package com.ysmjjsy.goya.module.wechat.configuration;

import com.ysmjjsy.goya.component.common.pojo.enums.AccountType;
import com.ysmjjsy.goya.module.wechat.annotation.ConditionalOnWxAppEnabled;
import com.ysmjjsy.goya.module.wechat.annotation.ConditionalOnWxMppEnabled;
import com.ysmjjsy.goya.module.wechat.api.processor.WxappProcessor;
import com.ysmjjsy.goya.module.wechat.api.service.WxApi;
import com.ysmjjsy.goya.module.wechat.mpp.processor.WxMppProcessor;
import com.ysmjjsy.goya.module.wechat.mpp.processor.WxmppLogHandler;
import com.ysmjjsy.goya.module.wechat.properties.WxAppProperties;
import com.ysmjjsy.goya.module.wechat.properties.WxMppProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:32
 */
@Slf4j
@AutoConfiguration
public class WeChatConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [wechat] configure.");
    }

    @ConditionalOnWxAppEnabled
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(WxAppProperties.class)
    static class WxAppConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Goya] |- module [wechat wx app] configure.");
        }

        @Bean
        @ConditionalOnMissingBean
        public WxappProcessor wxappProcessor(WxAppProperties wxAppProperties) {
            WxappProcessor wxappProcessor = new WxappProcessor(wxAppProperties);
            log.trace("[Goya] |- Bean [Wxapp Processor] Configure.");
            return wxappProcessor;
        }

        @Bean(AccountType.WECHAT_MINI_APP_HANDLER)
        @ConditionalOnBean(WxappProcessor.class)
        @ConditionalOnMissingBean
        public WxApi wxAppService(WxappProcessor wxappProcessor) {
            WxApi wxAppService = new WxApi(wxappProcessor);
            log.debug("[Goya] |- Bean [Wxapp Access Handler] Auto Configure.");
            return wxAppService;
        }
    }

    @ConditionalOnWxMppEnabled
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(WxMppProperties.class)
    static class WxMppConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Goya] |- module [wechat wx mpp] configure.");
        }

        @Bean
        @ConditionalOnMissingBean
        public WxMppProcessor wxmppProcessor(WxMppProperties wxMppProperties, StringRedisTemplate stringRedisTemplate) {
            WxMppProcessor wxMppProcessor = new WxMppProcessor();
            wxMppProcessor.setWxMppProperties(wxMppProperties);
            wxMppProcessor.setWxmppLogHandler(new WxmppLogHandler());
            wxMppProcessor.setStringRedisTemplate(stringRedisTemplate);
            log.trace("[Goya] |- Bean [WxMpp Processor] Configure.");
            return wxMppProcessor;
        }
    }
}
