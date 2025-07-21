package com.ysmjjsy.goya.security.authentication.provider;

import com.ysmjjsy.goya.component.common.utils.ListUtils;
import com.ysmjjsy.goya.component.exception.request.RequestInvalidException;
import com.ysmjjsy.goya.component.web.crypto.HttpCryptoProcessor;
import com.ysmjjsy.goya.component.web.utils.RequestUtils;
import com.ysmjjsy.goya.security.authentication.utils.OAuth2EndpointUtils;
import com.ysmjjsy.goya.security.core.constants.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * <p>Description: 抽象的认证 Converter </p>
 *
 * @author goya
 * @since 2023/6/21 6:23
 */
public abstract class AbstractAuthenticationConverter implements AuthenticationConverter {

    private final HttpCryptoProcessor httpCryptoProcessor;

    protected AbstractAuthenticationConverter(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    protected String[] decrypt(HttpServletRequest request, String requestId, List<String> parameters) {
        if (RequestUtils.isCryptoEnabled(request, requestId) && CollectionUtils.isNotEmpty(parameters)) {
            List<String> result = parameters.stream().map(item -> decrypt(request, requestId, item)).toList();
            return ListUtils.toStringArray(result);
        }

        return ListUtils.toStringArray(parameters);
    }

    protected String decrypt(HttpServletRequest request, String requestId, String parameter) {
        if (RequestUtils.isCryptoEnabled(request, requestId) && StringUtils.isNotBlank(parameter)) {
            try {
                return httpCryptoProcessor.decrypt(requestId, parameter);
            } catch (RequestInvalidException e) {
                OAuth2EndpointUtils.throwError(
                        SecurityConstants.SESSION_EXPIRED,
                        e.getMessage(),
                        OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
            }
        }
        return parameter;
    }

    protected Authentication getClientPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    protected Map<String, Object> getAdditionalParameters(HttpServletRequest request, MultiValueMap<String, String> parameters) {

        String requestId = RequestUtils.analyseRequestId(request);

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !key.equals(OAuth2ParameterNames.SCOPE)) {
                additionalParameters.put(key, (value.size() == 1) ? decrypt(request, requestId, value.get(0)) : decrypt(request, requestId, value));
            }
        });

        return additionalParameters;
    }

    protected Set<String> getRequestedScopes(String scope) {

        Set<String> requestedScopes = null;
        if (org.springframework.util.StringUtils.hasText(scope)) {
            requestedScopes = new HashSet<>(
                    Arrays.asList(org.springframework.util.StringUtils.delimitedListToStringArray(scope, " ")));
        }

        return requestedScopes;
    }
}
