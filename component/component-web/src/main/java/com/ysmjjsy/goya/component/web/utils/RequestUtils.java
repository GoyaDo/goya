package com.ysmjjsy.goya.component.web.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServerHttpRequest;

/**
 * <p>Description: Request 操作工具类 </p>
 *
 * @author goya
 * @since 2023/9/2 15:30
 */
public class RequestUtils {
    /**
     * 解析 Request ID
     * <p>
     * 如果请求中有 X_GOYA_REQUEST_ID 头，那么则返回 requestId，意味着前后端加密有效。
     * 这种处理方式，主要解决在没有使用系统 Request 的环境下，单独调用接口特别是测试接口时，提示 Request 过期的问题。
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @return session ID 或者 null
     */
    public static String analyseRequestId(HttpServletRequest httpServletRequest) {
        return HeaderUtils.getGoyaRequestId(httpServletRequest);
    }

    /**
     * 解析 Request ID
     * <p>
     * 如果请求中有 X_GOYA_REQUEST_ID 头，那么则返回 requestId，意味着前后端加密有效。
     * 这种处理方式，主要解决在没有使用系统 Request 的环境下，单独调用接口特别是测试接口时，提示 Request 过期的问题。
     *
     * @param serverHttpRequest {@link ServerHttpRequest}
     * @return session ID 或者 null
     */
    public static String analyseRequestId(ServerHttpRequest serverHttpRequest) {
        return HeaderUtils.getGoyaRequestId(serverHttpRequest);
    }

    /**
     * 解析 Request ID
     * <p>
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @return session ID 或者 null
     */
    public static String analyseRequestId(HttpInputMessage httpInputMessage) {
        return HeaderUtils.getGoyaRequestId(httpInputMessage);
    }

    /**
     * 判断基于 Request 的前后端数据加密是否开启
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param requestId          requestId
     * @return true 已开启，false 未开启。
     */
    public static boolean isCryptoEnabled(HttpServletRequest httpServletRequest, String requestId) {
        return HeaderUtils.hasGoyaRequestIdHeader(httpServletRequest) && StringUtils.isNotBlank(requestId);
    }

    /**
     * 判断基于 Request 的前后端数据加密是否开启
     *
     * @param httpInputMessage {@link HttpInputMessage}
     * @param requestId        requestId
     * @return true 已开启，false 未开启。
     */
    public static boolean isCryptoEnabled(HttpInputMessage httpInputMessage, String requestId) {
        return HeaderUtils.hasGoyaRequestIdHeader(httpInputMessage) && StringUtils.isNotBlank(requestId);
    }
}
