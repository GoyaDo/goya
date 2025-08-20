package com.ysmjjsy.goya.module.sms.stamp;

import cn.hutool.v7.core.util.RandomUtil;
import com.ysmjjsy.goya.component.cache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.module.sms.constants.SmsConstants;
import com.ysmjjsy.goya.module.sms.properties.SmsProperties;
import lombok.Setter;

/**
 * <p>Description: 手机短信验证码签章 </p>
 *
 * @author goya
 * @since 2021/8/26 17:44
 */
@Setter
public class VerificationCodeStampManager extends AbstractStampManager<String, String> {

    private SmsProperties smsProperties;

    public VerificationCodeStampManager() {
        super(SmsConstants.CACHE_NAME_TOKEN_VERIFICATION_CODE);
    }

    @Override
    public String nextStamp(String key) {
        if (smsProperties.getSandbox()) {
            return smsProperties.getTestCode();
        } else {
            return RandomUtil.randomNumbers(smsProperties.getLength());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(smsProperties.getExpire());
    }

    public Boolean getSandbox() {
        return smsProperties.getSandbox();
    }

    public String getVerificationCodeTemplateId() {
        return smsProperties.getVerificationCodeTemplateId();
    }
}
