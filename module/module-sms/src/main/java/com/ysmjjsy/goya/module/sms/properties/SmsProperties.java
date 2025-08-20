package com.ysmjjsy.goya.module.sms.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.module.sms.constants.SmsConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p>Description: 短信验证码配置属性 </p>
 *
 * @author goya
 * @since 2021/5/26 17:02
 */
@Setter
@Getter
@ConfigurationProperties(prefix = SmsConstants.PROPERTY_PREFIX_SMS)
public class SmsProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 启用短信沙盒测试模式
     */
    private Boolean sandbox = false;

    /**
     * 短信沙盒测试模式中，创建的默认验证码。
     */
    private String testCode = "123456";

    /**
     * 验证码短信模版名称
     */
    private String verificationCodeTemplateId = "VERIFICATION_CODE";

    /**
     * 超时时长，默认5分钟
     */
    private Duration expire = Duration.ofMinutes(5);

    /**
     * 手机验证码长度，默认为6位数
     */
    private int length = 6;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("sandbox", sandbox)
                .add("testCode", testCode)
                .add("verificationCodeTemplateId", verificationCodeTemplateId)
                .add("expire", expire)
                .add("length", length)
                .toString();
    }
}
