package com.ysmjjsy.goya.component.web.support;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * <p>Description: RestTemplate 统一响应错误处理器 </p>
 * <p>
 * 默认的 RestTemplate 有个机制是请求状态码非200 就抛出异常，会中断接下来的操作。
 * 如果不想中断对结果数据得解析，可以通过覆盖默认的 ResponseErrorHandler ，
 * 对hasError修改下，让他一直返回true，即是不检查状态码及抛异常了
 *
 * @author goya
 * @since 2024/2/28 22:55
 */
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return true;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

    }
}
