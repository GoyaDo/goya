package com.ysmjjsy.goya.security.authentication.provider;

import com.ysmjjsy.goya.component.crypto.web.HttpCryptoProcessor;
import com.ysmjjsy.goya.security.core.utils.OAuth2EndpointUtils;
import com.ysmjjsy.goya.security.core.constants.GoyaGrantType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;

/**
 * <p>Description: 自定义密码模式认证转换器 </p>
 * <p>
 * {@code AuthenticationConverter} 类似于以前的 {@code AbstractTokenGranter}
 * 主要用途是从请求中获取参数，并拼装Token类
 *
 * @author goya
 * @since 2022/2/22 17:03
 */
public final class OAuth2ResourceOwnerPasswordAuthenticationConverter extends AbstractAuthenticationConverter {

    public OAuth2ResourceOwnerPasswordAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        super(httpCryptoProcessor);
    }

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!GoyaGrantType.PASSWORD.getValue().equals(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getParameters(request);

        // scope (OPTIONAL)
        String scope = OAuth2EndpointUtils.checkOptionalParameter(parameters, OAuth2ParameterNames.SCOPE);

        // username (REQUIRED)
        OAuth2EndpointUtils.checkRequiredParameter(parameters, OAuth2ParameterNames.USERNAME);

        // password (REQUIRED)
        OAuth2EndpointUtils.checkRequiredParameter(parameters, OAuth2ParameterNames.PASSWORD);

        return new OAuth2ResourceOwnerPasswordAuthenticationToken(getClientPrincipal(), getRequestedScopes(scope), getAdditionalParameters(request, parameters));
    }
}
