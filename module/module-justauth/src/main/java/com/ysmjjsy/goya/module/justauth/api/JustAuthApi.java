package com.ysmjjsy.goya.module.justauth.api;

import com.ysmjjsy.goya.module.justauth.processor.JustAuthProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:23
 */
@Slf4j
@RequiredArgsConstructor
public class JustAuthApi {

    private final JustAuthProcessor justAuthProcessor;

    /**
     * 授权地址
     * @param core 第三方登录的类别
     * @return 返回授权地址
     */
    public String getAuthorizeUrl(String core) {
        return justAuthProcessor.getAuthorizeUrl(core);
    }
}
