package com.ysmjjsy.goya.module.sms.api;

import com.ysmjjsy.goya.component.common.constants.DefaultConstants;
import com.ysmjjsy.goya.module.sms.stamp.VerificationCodeStampManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.entity.SmsResponse;
import org.dromara.sms4j.core.factory.SmsFactory;

import java.util.LinkedHashMap;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:13
 */
@Slf4j
@RequiredArgsConstructor
public class SmsApi {

    private final VerificationCodeStampManager verificationCodeStampManager;

    /**
     * 发送短信
     *
     * @param phone 手机号
     * @return 结果
     */
    public boolean sendSms(String phone) {
        String code = verificationCodeStampManager.create(phone);
        boolean result;
        if (verificationCodeStampManager.getSandbox()) {
            result = true;
        } else {
            SmsBlend smsBlend = SmsFactory.getSmsBlend();
            LinkedHashMap<String, String> message = new LinkedHashMap<>();
            message.put(DefaultConstants.CODE, code);
            SmsResponse smsResponse = smsBlend.sendMessage(phone, verificationCodeStampManager.getVerificationCodeTemplateId(), message);
            result = smsResponse.isSuccess();
        }
        return result;
    }
}
