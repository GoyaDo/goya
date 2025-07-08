package com.ysmjjsy.goya.module.sms.configuration;

import com.ysmjjsy.goya.component.dto.enums.AccountType;
import com.ysmjjsy.goya.module.sms.annotation.ConditionalOnSmsEnabled;
import com.ysmjjsy.goya.module.sms.properties.SmsProperties;
import com.ysmjjsy.goya.module.sms.service.SmsService;
import com.ysmjjsy.goya.module.sms.stamp.VerificationCodeStampManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>Description: 发送短信统一配置 </p>
 *
 * @author goya
 * @since 2021/5/25 12:03
 */
@AutoConfiguration
@ConditionalOnSmsEnabled
@EnableConfigurationProperties({SmsProperties.class})
public class SmsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SmsConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- module [sms] configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public VerificationCodeStampManager verificationCodeStampManager(SmsProperties smsProperties) {
        VerificationCodeStampManager verificationCodeStampManager = new VerificationCodeStampManager();
        verificationCodeStampManager.setSmsProperties(smsProperties);
        log.trace("[Goya] |- Bean [Verification Code Stamp Manager] Configure.");
        return verificationCodeStampManager;
    }

    @Bean(AccountType.PHONE_NUMBER_HANDLER)
    public SmsService smsService(VerificationCodeStampManager verificationCodeStampManager) {
        SmsService smsService = new SmsService(verificationCodeStampManager);
        log.trace("[Goya] |- Bean [Phone Number SignIn Handler] Configure.");
        return smsService;
    }
}
