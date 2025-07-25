package com.ysmjjsy.goya.component.web.crypto;

import com.ysmjjsy.goya.component.exception.request.RequestInvalidException;
import com.ysmjjsy.goya.component.web.annotation.Crypto;
import com.ysmjjsy.goya.component.web.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: @RequestParam 解密处理器 </p>
 *
 * @author goya
 * @since 2021/10/10 12:17
 */
public class DecryptRequestParamResolver implements HandlerMethodArgumentResolver {

    private static final Logger log = LoggerFactory.getLogger(DecryptRequestParamResolver.class);

    private HttpCryptoProcessor httpCryptoProcessor;
    private RequestParamMethodArgumentResolver requestParamMethodArgumentResolver;

    public void setRequestParamMethodArgumentResolver(RequestParamMethodArgumentResolver requestParamMethodArgumentResolver) {
        this.requestParamMethodArgumentResolver = requestParamMethodArgumentResolver;
    }

    public void setInterfaceCryptoProcessor(HttpCryptoProcessor httpCryptoProcessor) {
        this.httpCryptoProcessor = httpCryptoProcessor;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        String methodName = methodParameter.getMethod().getName();
        boolean isSupports = isConfigCrypto(methodParameter) && requestParamMethodArgumentResolver.supportsParameter(methodParameter);

        log.trace("[Goya] |- Is DecryptRequestParamResolver supports method [{}] ? Status is [{}].", methodName, isSupports);
        return isSupports;
    }

    /**
     * 判断该接口方法是否用@Crypto注解标记，同时requestDecrypt的值是true
     *
     * @param methodParameter {@link MethodParameter}
     * @return 是否开启了自定义@Crypto
     */
    private boolean isConfigCrypto(MethodParameter methodParameter) {
        Crypto crypto = methodParameter.getMethodAnnotation(Crypto.class);
        return ObjectUtils.isNotEmpty(crypto) && crypto.requestDecrypt();
    }

    /**
     * 是否是常规的请求
     *
     * @param webRequest {@link NativeWebRequest}
     * @return boolean
     */
    private boolean isRegularRequest(NativeWebRequest webRequest) {
        MultipartRequest multipartRequest = webRequest.getNativeRequest(MultipartRequest.class);
        return ObjectUtils.isEmpty(multipartRequest);
    }

    private String[] decrypt(String requestId, String[] paramValues) throws RequestInvalidException {
        List<String> values = new ArrayList<>();
        for (String paramValue : paramValues) {
            String value = httpCryptoProcessor.decrypt(requestId, paramValue);
            if (StringUtils.isNotBlank(value)) {
                values.add(value);
            }
        }

        String[] result = new String[values.size()];
        return values.toArray(result);
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        if (isRegularRequest(webRequest)) {

            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

            String requestId = RequestUtils.analyseRequestId(request);
            if (RequestUtils.isCryptoEnabled(request, requestId)) {
                String[] paramValues = request.getParameterValues(methodParameter.getParameterName());
                if (ArrayUtils.isNotEmpty(paramValues)) {
                    String[] values = decrypt(requestId, paramValues);
                    return (values.length == 1 ? values[0] : values);
                }
            } else {
                log.warn("[Goya] |- Cannot find Goya Cloud custom session header. Use interface crypto founction need add X_GOYA_REQUEST_ID to request header.");
            }
        }

        log.debug("[Goya] |- The decryption conditions are not met DecryptRequestParamResolver, skip! to next!");
        return requestParamMethodArgumentResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
    }
}
