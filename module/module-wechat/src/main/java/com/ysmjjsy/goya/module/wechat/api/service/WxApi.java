package com.ysmjjsy.goya.module.wechat.api.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ysmjjsy.goya.component.common.exception.access.AccessPreProcessFailedException;
import com.ysmjjsy.goya.module.wechat.api.processor.WxappProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:36
 */
@Slf4j
@RequiredArgsConstructor
public class WxApi {

    private final WxappProcessor wxappProcessor;

    /**
     * 小程序登录
     *
     * @param code  小程序生成的 code
     * @param appId 小程序 AppId
     * @return {@link WxMaJscode2SessionResult} 对象
     */
    public WxMaJscode2SessionResult login(String code, String appId) {
        WxMaJscode2SessionResult wxMaSession = wxappProcessor.login(code, appId);
        if (ObjectUtils.isNotEmpty(wxMaSession)) {
            return wxMaSession;
        }
        throw new AccessPreProcessFailedException("WxApp login failed");
    }

}
