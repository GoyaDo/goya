package com.ysmjjsy.goya.component.captcha.configuration;

import com.ysmjjsy.goya.component.captcha.processor.CaptchaRendererFactory;
import com.ysmjjsy.goya.component.captcha.properties.CaptchaProperties;
import com.ysmjjsy.goya.component.captcha.provider.ResourceProvider;
import com.ysmjjsy.goya.component.captcha.renderer.behavior.JigsawCaptchaRenderer;
import com.ysmjjsy.goya.component.captcha.renderer.behavior.WordClickCaptchaRenderer;
import com.ysmjjsy.goya.component.captcha.renderer.graphic.*;
import com.ysmjjsy.goya.component.captcha.renderer.hutool.CircleCaptchaRenderer;
import com.ysmjjsy.goya.component.captcha.renderer.hutool.GifCaptchaRenderer;
import com.ysmjjsy.goya.component.captcha.renderer.hutool.LineCaptchaRenderer;
import com.ysmjjsy.goya.component.captcha.renderer.hutool.ShearCaptchaRenderer;
import com.ysmjjsy.goya.component.pojo.enums.CaptchaCategory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/14 15:35
 */
@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [captcha] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public CaptchaRendererFactory captchaRendererFactory() {
        CaptchaRendererFactory captchaRendererFactory = new CaptchaRendererFactory();
        log.trace("[Goya] |- Bean [Captcha Renderer Factory] Configure.");
        return captchaRendererFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    public ResourceProvider resourceProvider(CaptchaProperties captchaProperties) {
        ResourceProvider resourceProvider = new ResourceProvider(captchaProperties);
        log.trace("[Goya] |- Bean [Resource Provider] Configure.");
        return resourceProvider;
    }

    @Configuration(proxyBeanMethods = false)
    static class BehaviorCaptchaConfiguration {

        @Bean(CaptchaCategory.JIGSAW_CAPTCHA)
        public JigsawCaptchaRenderer jigsawCaptchaRenderer(ResourceProvider resourceProvider) {
            JigsawCaptchaRenderer jigsawCaptchaRenderer = new JigsawCaptchaRenderer();
            jigsawCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Jigsaw Captcha Renderer] Configure.");
            return jigsawCaptchaRenderer;
        }

        @Bean(CaptchaCategory.WORD_CLICK_CAPTCHA)
        public WordClickCaptchaRenderer wordClickCaptchaRenderer(ResourceProvider resourceProvider) {
            WordClickCaptchaRenderer wordClickCaptchaRenderer = new WordClickCaptchaRenderer();
            wordClickCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Word Click Captcha Renderer] Configure.");
            return wordClickCaptchaRenderer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class GraphicCaptchaConfiguration {

        @Bean(CaptchaCategory.ARITHMETIC_CAPTCHA)
        public ArithmeticCaptchaRenderer arithmeticCaptchaRenderer(ResourceProvider resourceProvider) {
            ArithmeticCaptchaRenderer arithmeticCaptchaRenderer = new ArithmeticCaptchaRenderer();
            arithmeticCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Arithmetic Captcha Renderer] Configure.");
            return arithmeticCaptchaRenderer;
        }

        @Bean(CaptchaCategory.CHINESE_CAPTCHA)
        public ChineseCaptchaRenderer chineseCaptchaRenderer(ResourceProvider resourceProvider) {
            ChineseCaptchaRenderer chineseCaptchaRenderer = new ChineseCaptchaRenderer();
            chineseCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Chinese Captcha Renderer] Configure.");
            return chineseCaptchaRenderer;
        }

        @Bean(CaptchaCategory.CHINESE_GIF_CAPTCHA)
        public ChineseGifCaptchaRenderer chineseGifCaptchaRenderer(ResourceProvider resourceProvider) {
            ChineseGifCaptchaRenderer chineseGifCaptchaRenderer = new ChineseGifCaptchaRenderer();
            chineseGifCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Chinese Gif Captcha Renderer] Configure.");
            return chineseGifCaptchaRenderer;
        }

        @Bean(CaptchaCategory.SPEC_GIF_CAPTCHA)
        public SpecGifCaptchaRenderer specGifCaptchaRenderer(ResourceProvider resourceProvider) {
            SpecGifCaptchaRenderer specGifCaptchaRenderer = new SpecGifCaptchaRenderer();
            specGifCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Spec Gif Captcha Renderer] Configure.");
            return specGifCaptchaRenderer;
        }

        @Bean(CaptchaCategory.SPEC_CAPTCHA)
        public SpecCaptchaRenderer specCaptchaRenderer(ResourceProvider resourceProvider) {
            SpecCaptchaRenderer specCaptchaRenderer = new SpecCaptchaRenderer();
            specCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Spec Captcha Renderer] Configure.");
            return specCaptchaRenderer;
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class HutoolCaptchaConfiguration {

        @Bean(CaptchaCategory.HUTOOL_LINE_CAPTCHA)
        public LineCaptchaRenderer lineCaptchaRenderer(ResourceProvider resourceProvider) {
            LineCaptchaRenderer lineCaptchaRenderer = new LineCaptchaRenderer();
            lineCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Hutool Line Captcha Renderer] Configure.");
            return lineCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_CIRCLE_CAPTCHA)
        public CircleCaptchaRenderer circleCaptchaRenderer(ResourceProvider resourceProvider) {
            CircleCaptchaRenderer circleCaptchaRenderer = new CircleCaptchaRenderer();
            circleCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Hutool Circle Captcha Renderer] Configure.");
            return circleCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_SHEAR_CAPTCHA)
        public ShearCaptchaRenderer shearCaptchaRenderer(ResourceProvider resourceProvider) {
            ShearCaptchaRenderer shearCaptchaRenderer = new ShearCaptchaRenderer();
            shearCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Hutool Shear Captcha Renderer] Configure.");
            return shearCaptchaRenderer;
        }

        @Bean(CaptchaCategory.HUTOOL_GIF_CAPTCHA)
        public GifCaptchaRenderer gifCaptchaRenderer(ResourceProvider resourceProvider) {
            GifCaptchaRenderer gifCaptchaRenderer = new GifCaptchaRenderer();
            gifCaptchaRenderer.setResourceProvider(resourceProvider);
            log.trace("[Goya] |- Bean [Hutool Gif Captcha Renderer] Configure.");
            return gifCaptchaRenderer;
        }
    }
}
