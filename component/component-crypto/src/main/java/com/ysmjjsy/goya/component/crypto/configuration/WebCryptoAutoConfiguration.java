package com.ysmjjsy.goya.component.crypto.configuration;

import com.google.common.collect.Lists;
import com.ysmjjsy.goya.component.crypto.web.DecryptRequestParamMapResolver;
import com.ysmjjsy.goya.component.crypto.web.DecryptRequestParamResolver;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 11:00
 */
@Slf4j
@AutoConfiguration(after = CryptoAutoConfiguration.class)
public class WebCryptoAutoConfiguration {

    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final DecryptRequestParamResolver decryptRequestParamResolver;
    private final DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [crypto] WebCryptoAutoConfiguration auto configure.");
        List<HandlerMethodArgumentResolver> unmodifiableList = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> list = Lists.newArrayList();
        for (HandlerMethodArgumentResolver methodArgumentResolver : unmodifiableList) {
            //需要在requestParam之前执行自定义逻辑，然后再执行下一个逻辑（责任链模式）
            if (methodArgumentResolver instanceof RequestParamMapMethodArgumentResolver) {
                decryptRequestParamMapResolver.setRequestParamMapMethodArgumentResolver((RequestParamMapMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamMapResolver);
            }
            if (methodArgumentResolver instanceof RequestParamMethodArgumentResolver) {
                decryptRequestParamResolver.setRequestParamMethodArgumentResolver((RequestParamMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamResolver);
            }
            list.add(methodArgumentResolver);
        }
        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }

    public WebCryptoAutoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, DecryptRequestParamResolver decryptRequestParamResolver, DecryptRequestParamMapResolver decryptRequestParamMapResolver) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.decryptRequestParamResolver = decryptRequestParamResolver;
        this.decryptRequestParamMapResolver = decryptRequestParamMapResolver;
    }
}
