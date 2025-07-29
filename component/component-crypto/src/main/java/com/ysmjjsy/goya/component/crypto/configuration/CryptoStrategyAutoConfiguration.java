package com.ysmjjsy.goya.component.crypto.configuration;

import com.ysmjjsy.goya.component.crypto.definition.AsymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.definition.SymmetricCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.AESCryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.RSACryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.SM2CryptoProcessor;
import com.ysmjjsy.goya.component.crypto.processor.SM4CryptoProcessor;
import com.ysmjjsy.goya.component.crypto.properties.CryptoProperties;
import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
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
public class CryptoStrategyAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CryptoStrategyAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [protect crypto strategy] configure.");
    }

    @ConditionalOnProperty(name = GoyaConstants.PROPERTY_ASSISTANT_CRYPTO_STRATEGY, havingValue = "SM")
    @Configuration(proxyBeanMethods = false)
    static class SMCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor sm2CryptoProcessor() {
            SM2CryptoProcessor sm2CryptoProcessor = new SM2CryptoProcessor();
            log.trace("[Goya] |- Strategy [SM Asymmetric SM2 Crypto Processor] Configure.");
            return sm2CryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor sm4CryptoProcessor() {
            SM4CryptoProcessor sm4CryptoProcessor = new SM4CryptoProcessor();
            log.trace("[Goya] |- Strategy [SM Symmetric SM4 Crypto Processor] Configure.");
            return sm4CryptoProcessor;
        }
    }

    @ConditionalOnProperty(name = GoyaConstants.PROPERTY_ASSISTANT_CRYPTO_STRATEGY, havingValue = "STANDARD")
    @Configuration(proxyBeanMethods = false)
    static class StandardCryptoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public AsymmetricCryptoProcessor rsaCryptoProcessor() {
            RSACryptoProcessor rsaCryptoProcessor = new RSACryptoProcessor();
            log.trace("[Goya] |- Strategy [Standard Asymmetric RSA Crypto Processor] Configure.");
            return rsaCryptoProcessor;
        }

        @Bean
        @ConditionalOnMissingBean
        public SymmetricCryptoProcessor aesCryptoProcessor() {
            AESCryptoProcessor aesCryptoProcessor = new AESCryptoProcessor();
            log.trace("[Goya] |- Strategy [Standard Symmetric AES Crypto Processor] Configure.");
            return aesCryptoProcessor;
        }
    }
}
