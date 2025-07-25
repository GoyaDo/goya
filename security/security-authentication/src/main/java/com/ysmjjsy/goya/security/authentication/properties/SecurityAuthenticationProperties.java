package com.ysmjjsy.goya.security.authentication.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

import java.time.Duration;

/**
 * <p>Description: OAuth2 合规性配置参数 </p>
 *
 * @author goya
 * @since 2022/7/7 0:16
 */
@Setter
@Getter
@ConfigurationProperties(prefix = SecurityConstants.PROPERTY_REFIX_SECURITY_AUTHENTICATION)
public class SecurityAuthenticationProperties {

    /**
     * 开启登录失败限制
     */
    private SignInFailureLimited signInFailureLimited = new SignInFailureLimited();

    /**
     * 同一终端登录限制
     */
    private SignInEndpointLimited signInEndpointLimited = new SignInEndpointLimited();

    /**
     * 账户踢出限制
     */
    private SignInKickOutLimited signInKickOutLimited = new SignInKickOutLimited();

    private FormLogin formLogin = new FormLogin();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("signInEndpointLimited", signInEndpointLimited)
                .add("signInFailureLimited", signInFailureLimited)
                .add("signInKickOutLimited", signInKickOutLimited)
                .toString();
    }

    @Setter
    @Getter
    public static class SignInFailureLimited {
        /**
         * 是否开启登录失败检测，默认开启
         */
        private Boolean enabled = true;

        /**
         * 允许最大失败次数
         */
        private Integer maxTimes = 5;

        /**
         * 是否自动解锁被锁定用户，默认开启
         */
        private Boolean autoUnlock = true;

        /**
         * 记录失败次数的缓存过期时间，默认：2小时。
         */
        private Duration expire = Duration.ofHours(2);

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .add("maxTimes", maxTimes)
                    .add("autoUnlock", autoUnlock)
                    .add("expire", expire)
                    .toString();
        }
    }

    @Setter
    @Getter
    public static class SignInEndpointLimited {
        /**
         * 同一终端登录限制是否开启，默认关闭。
         */
        private Boolean enabled = false;

        /**
         * 同一终端，允许同时登录的最大数量
         */
        private Integer maximum = 1;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .add("maximum", maximum)
                    .toString();
        }
    }

    @Setter
    @Getter
    public static class SignInKickOutLimited {
        /**
         * 是否开启 Session 踢出功能，默认开启
         */
        private Boolean enabled = true;

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("enabled", enabled)
                    .toString();
        }
    }

    @Setter
    @Getter
    public static class FormLogin {
        /**
         * UI 界面用户名标输入框 name 属性值
         */
        private String usernameParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY;
        /**
         * UI 界面密码标输入框 name 属性值
         */
        private String passwordParameter = UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY;
        /**
         * UI 界面Remember Me name 属性值
         */
        private String rememberMeParameter = AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY;
        /**
         * UI 界面验证码 name 属性值
         */
        private String captchaParameter = "captcha";
        /**
         * 登录页面地址
         */
        private String loginPageUrl = DefaultLoginPageGeneratingFilter.DEFAULT_LOGIN_PAGE_URL;
        /**
         * 登录失败重定向地址
         */
        private String failureUrl = loginPageUrl + SymbolConstants.QUESTION + DefaultLoginPageGeneratingFilter.ERROR_PARAMETER_NAME;
        /**
         * 表单登录认证地址
         */
        private String authenticationUrl;
        /**
         * 注销成功地址
         */
        private String logoutSuccessUrl = DefaultLoginPageGeneratingFilter.DEFAULT_LOGIN_PAGE_URL + "?logout";
        /**
         * 自定义用户注册页面地址
         */
        private String registrationUrl;
        /**
         * 自定义忘记密码页面地址
         */
        private String forgotPasswordUrl;

        /**
         * Cookie 有效期，默认：30天
         */
        private Duration cookieMaxAge = Duration.ofDays(30);
        /**
         * 验证码是否开启，默认 true，显示
         */
        private Boolean captchaEnabled = true;
        /**
         * 验证码类别，默认为 Hutool Gif 类型
         */
        private String category = "HUTOOL_GIF";

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("authenticationUrl", authenticationUrl)
                    .add("usernameParameter", usernameParameter)
                    .add("passwordParameter", passwordParameter)
                    .add("rememberMeParameter", rememberMeParameter)
                    .add("captchaParameter", captchaParameter)
                    .add("loginPageUrl", loginPageUrl)
                    .add("failureUrl", failureUrl)
                    .add("logoutSuccessUrl", logoutSuccessUrl)
                    .add("registrationUrl", registrationUrl)
                    .add("forgotPasswordUrl", forgotPasswordUrl)
                    .add("cookieMaxAge", cookieMaxAge)
                    .add("captchaEnabled", captchaEnabled)
                    .add("category", category)
                    .toString();
        }

        public Boolean isRegistrationEnabled() {
            return StringUtils.isNotBlank(registrationUrl);
        }

        public Boolean isForgotPasswordEnabled() {
            return StringUtils.isNotBlank(forgotPasswordUrl);
        }
    }
}
