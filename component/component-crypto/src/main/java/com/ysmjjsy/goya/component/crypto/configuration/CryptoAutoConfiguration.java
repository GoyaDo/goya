package com.ysmjjsy.goya.component.crypto.configuration;

import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.crypto.configuration.properties.CryptoProperties;
import com.ysmjjsy.goya.component.crypto.definition.AsymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.definition.SymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.AESCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.RSACryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.SM2CryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.SM4CryptoProcessor;
import com.ysmjjsy.goya.component.crypto.web.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: 非对称算法配置 </p>
 *
 * @author goya
 * @since 2022/5/2 15:26
 */
@AutoConfiguration
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CryptoAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [crypto] CryptoStrategyAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpCryptoProcessor httpCryptoProcessor(AsymmetricCryptoProcessor asymmetricCryptoProcessor, SymmetricCryptoProcessor symmetricCryptoProcessor) {
        HttpCryptoProcessor httpCryptoProcessor = new HttpCryptoProcessor(asymmetricCryptoProcessor, symmetricCryptoProcessor);
        log.trace("[Goya] |- component [crypto] |- bean [httpCryptoProcessor] register.");
        return httpCryptoProcessor;
    }

    @Bean
    @ConditionalOnClass(HttpCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestBodyAdvice decryptRequestBodyAdvice = new DecryptRequestBodyAdvice();
        decryptRequestBodyAdvice.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Goya] |- component [crypto] |- bean [decryptRequestBodyAdvice] register.");
        return decryptRequestBodyAdvice;
    }

    @Bean
    @ConditionalOnClass(HttpCryptoProcessor.class)
    @ConditionalOnMissingBean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice(HttpCryptoProcessor httpCryptoProcessor) {
        EncryptResponseBodyAdvice encryptResponseBodyAdvice = new EncryptResponseBodyAdvice();
        encryptResponseBodyAdvice.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Goya] |- component [crypto] |- bean [encryptResponseBodyAdvice] register.");
        return encryptResponseBodyAdvice;
    }

    @Bean
    @ConditionalOnClass(HttpCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestParamMapResolver decryptRequestParamStringResolver(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestParamMapResolver decryptRequestParamMapResolver = new DecryptRequestParamMapResolver();
        decryptRequestParamMapResolver.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Goya] |- component [crypto] |- bean [decryptRequestParamStringResolver] register.");
        return decryptRequestParamMapResolver;
    }

    @Bean
    @ConditionalOnClass(HttpCryptoProcessor.class)
    @ConditionalOnMissingBean
    public DecryptRequestParamResolver decryptRequestParamResolver(HttpCryptoProcessor httpCryptoProcessor) {
        DecryptRequestParamResolver decryptRequestParamResolver = new DecryptRequestParamResolver();
        decryptRequestParamResolver.setInterfaceCryptoProcessor(httpCryptoProcessor);
        log.trace("[Goya] |- component [crypto] |- bean [decryptRequestParamResolver] register.");
        return decryptRequestParamResolver;
    }


    @ConditionalOnProperty(name = GoyaConstants.PROPERTY_ASSISTANT_CRYPTO_STRATEGY, havingValue = "SM")
    @Configuration(proxyBeanMethods = false)
    static class SMCryptoConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Goya] |- component [crypto] SMCryptoConfiguration auto configure.");
        }

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor sm2CryptoProcessor() {
            SM2CryptoProcessor sm2CryptoProcessor = new SM2CryptoProcessor();
            log.trace("[Goya] |- component [crypto] |- bean [sm2CryptoProcessor] register.");
            return sm2CryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor sm4CryptoProcessor() {
            SM4CryptoProcessor sm4CryptoProcessor = new SM4CryptoProcessor();
            log.trace("[Goya] |- component [crypto] |- bean [sm4CryptoProcessor] register.");
            return sm4CryptoProcessor;
        }
    }

    @ConditionalOnProperty(name = GoyaConstants.PROPERTY_ASSISTANT_CRYPTO_STRATEGY, havingValue = "STANDARD")
    @Configuration(proxyBeanMethods = false)
    static class StandardCryptoConfiguration {

        @PostConstruct
        public void postConstruct() {
            log.debug("[Goya] |- component [crypto] StandardCryptoConfiguration auto configure.");
        }

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor rsaCryptoProcessor() {
            RSACryptoProcessor rsaCryptoProcessor = new RSACryptoProcessor();
            log.trace("[Goya] |- component [crypto] |- bean [rsaCryptoProcessor] register.");
            return rsaCryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor aesCryptoProcessor() {
            AESCryptoProcessor aesCryptoProcessor = new AESCryptoProcessor();
            log.trace("[Goya] |- component [crypto] |- bean [aesCryptoProcessor] register.");
            return aesCryptoProcessor;
        }
    }
}
