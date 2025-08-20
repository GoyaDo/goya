package com.ysmjjsy.goya.security.authentication.provider;

import com.ysmjjsy.goya.component.common.pojo.enums.AccountType;
import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.security.core.utils.OAuth2EndpointUtils;
import com.ysmjjsy.goya.security.core.constants.GoyaGrantType;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * <p>Description: 社交认证 Converter </p>
 *
 * @author goya
 * @since 2022/3/31 14:16
 */
public class OAuth2SocialCredentialsAuthenticationConverter extends AbstractAuthenticationConverter {

    public OAuth2SocialCredentialsAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!GoyaGrantType.SOCIAL.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // scope (OPTIONAL)
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, OAuth2ParameterNames.SCOPE);

        // source (REQUIRED)
        String source = OAuth2EndpointUtils.checkRequiredParameter(parameters, SecurityConstants.SOURCE);
        // others (REQUIRED)
        // TODO：2022-03-31 这里主要是作为参数的检查，社交登录内容比较多，后续根据实际情况添加
        if (StringUtils.hasText(source)) {
            AccountType accountCategory = AccountType.getAccountType(source);
            if (ObjectUtils.isNotEmpty(accountCategory)) {
                switch (accountCategory.getHandler()) {
                    case AccountType.PHONE_NUMBER_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "mobile");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "code");
                        break;
                    case AccountType.WECHAT_MINI_APP_HANDLER:
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "appId");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "sessionKey");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "encryptedData");
                        OAuth2EndpointUtils.checkRequiredParameter(parameters, "iv");
                        break;
                    default:
                        break;
                }
            }
        }

        return new OAuth2SocialCredentialsAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), getAdditionalParameters(request, parameters));
    }
}
