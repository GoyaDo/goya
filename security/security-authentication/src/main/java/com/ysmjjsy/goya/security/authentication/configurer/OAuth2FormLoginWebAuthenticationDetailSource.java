package com.ysmjjsy.goya.security.authentication.configurer;

import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.component.web.utils.RequestUtils;
import com.ysmjjsy.goya.security.authentication.properties.SecurityAuthenticationProperties;
import com.ysmjjsy.goya.security.core.form.FormLoginWebAuthenticationDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;

/**
 * <p>Description: 表单登录 Details 定义 </p>
 *
 * @author goya
 * @since 2022/4/12 10:41
 */
public class OAuth2FormLoginWebAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, FormLoginWebAuthenticationDetails> {

    private final SecurityAuthenticationProperties authenticationProperties;
    private final HttpCryptoProcessor httpCryptoProcessor;


    public OAuth2FormLoginWebAuthenticationDetailSource(SecurityAuthenticationProperties authenticationProperties, HttpCryptoProcessor httpCryptoProcessor) {
        this.authenticationProperties = authenticationProperties;
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public FormLoginWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        String encryptedCode = request.getParameter(authenticationProperties.getFormLogin().getCaptchaParameter());

        String requestId = RequestUtils.analyseRequestId(request);

        String code = null;
        if (StringUtils.isNotBlank(requestId) && StringUtils.isNotBlank(encryptedCode)) {
            code = httpCryptoProcessor.decrypt(requestId, encryptedCode);
        }

        return new FormLoginWebAuthenticationDetails(request, authenticationProperties.getFormLogin().getCaptchaEnabled(), authenticationProperties.getFormLogin().getCaptchaParameter(), authenticationProperties.getFormLogin().getCategory(), code);
    }
}
