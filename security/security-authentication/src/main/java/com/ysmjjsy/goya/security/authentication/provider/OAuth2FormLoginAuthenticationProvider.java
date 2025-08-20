package com.ysmjjsy.goya.security.authentication.provider;

import com.ysmjjsy.goya.component.captcha.domain.Verification;
import com.ysmjjsy.goya.component.captcha.processor.CaptchaRendererFactory;
import com.ysmjjsy.goya.component.common.exception.captcha.CaptchaHasExpiredException;
import com.ysmjjsy.goya.component.common.exception.captcha.CaptchaIsEmptyException;
import com.ysmjjsy.goya.component.common.exception.captcha.CaptchaMismatchException;
import com.ysmjjsy.goya.component.common.exception.captcha.CaptchaParameterIllegalException;
import com.ysmjjsy.goya.security.core.exception.OAuth2CaptchaArgumentIllegalException;
import com.ysmjjsy.goya.security.core.exception.OAuth2CaptchaHasExpiredException;
import com.ysmjjsy.goya.security.core.exception.OAuth2CaptchaIsEmptyException;
import com.ysmjjsy.goya.security.core.exception.OAuth2CaptchaMismatchException;
import com.ysmjjsy.goya.security.core.form.FormLoginWebAuthenticationDetails;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>Description: OAuth2 (Security) 表单登录 Provider </p>
 * <p>
 * 扩展的OAuth2表单登录Provider，以支持表单登录的验证码
 *
 * @author goya
 * @since 2022/4/12 10:21
 * @see DaoAuthenticationProvider
 */
public class OAuth2FormLoginAuthenticationProvider extends DaoAuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(OAuth2FormLoginAuthenticationProvider.class);

    private final CaptchaRendererFactory captchaRendererFactory;

    public OAuth2FormLoginAuthenticationProvider(CaptchaRendererFactory captchaRendererFactory) {
        super();
        this.captchaRendererFactory = captchaRendererFactory;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object details = authentication.getDetails();

        if (ObjectUtils.isNotEmpty(details) && details instanceof FormLoginWebAuthenticationDetails formLoginWebAuthenticationDetails) {

            if (!formLoginWebAuthenticationDetails.getClosed()) {

                String code = formLoginWebAuthenticationDetails.getCode();
                String category = formLoginWebAuthenticationDetails.getCategory();
                String identity = formLoginWebAuthenticationDetails.getIdentity();

                if (StringUtils.isBlank(code)) {
                    throw new OAuth2CaptchaIsEmptyException("Captcha is empty.");
                }

                try {
                    Verification verification = new Verification();
                    verification.setCharacters(code);
                    verification.setCategory(category);
                    verification.setIdentity(identity);
                    captchaRendererFactory.verify(verification);
                } catch (CaptchaParameterIllegalException e) {
                    throw new OAuth2CaptchaArgumentIllegalException("Captcha argument is illegal!");
                } catch (CaptchaHasExpiredException e) {
                    throw new OAuth2CaptchaHasExpiredException("Captcha is expired!");
                } catch (CaptchaMismatchException e) {
                    throw new OAuth2CaptchaMismatchException("Captcha is mismatch!");
                } catch (CaptchaIsEmptyException e) {
                    throw new OAuth2CaptchaIsEmptyException("Captcha is empty!");
                }
            }
        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
        boolean supports = (OAuth2FormLoginAuthenticationToken.class.isAssignableFrom(authentication));
        log.trace("[Goya] |- Form Login Authentication is supports! [{}]", supports);
        return supports;
    }
}
