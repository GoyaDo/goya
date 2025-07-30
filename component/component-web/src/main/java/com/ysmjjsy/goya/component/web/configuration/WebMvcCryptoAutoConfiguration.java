package com.ysmjjsy.goya.component.web.configuration;

import com.google.common.collect.Lists;
import com.ysmjjsy.goya.component.web.config.HttpCryptoConfiguration;
import com.ysmjjsy.goya.component.web.crypto.DecryptRequestParamMapResolver;
import com.ysmjjsy.goya.component.web.crypto.DecryptRequestParamResolver;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * <p>Description: Rest 请求加解密配置 </p>
 *
 * @author goya
 * @since 2022/5/31 9:48
 */
@AutoConfiguration
@Import(HttpCryptoConfiguration.class)
public class WebMvcCryptoAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebMvcCryptoAutoConfiguration.class);
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final DecryptRequestParamResolver decryptRequestParamResolver;
    private final DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    public WebMvcCryptoAutoConfiguration(RequestMappingHandlerAdapter requestMappingHandlerAdapter, DecryptRequestParamResolver decryptRequestParamResolver, DecryptRequestParamMapResolver decryptRequestParamMapResolver) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.decryptRequestParamResolver = decryptRequestParamResolver;
        this.decryptRequestParamMapResolver = decryptRequestParamMapResolver;
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [web] WebMvcCryptoAutoConfiguration auto configure.");

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
        log.debug("[Goya] |- component [web] Crypto HandlerMethodArgumentResolver auto configure.");

        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }
}
