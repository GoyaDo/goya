package com.ysmjjsy.goya.module.wechat.app.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ysmjjsy.goya.component.exception.access.AccessPreProcessFailedException;
import com.ysmjjsy.goya.module.wechat.app.processor.WxappProcessor;
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
public class WxAppService {

    private final WxappProcessor wxappProcessor;

    /**
     * 小程序登录
     *
     * @param core code
     * @param params 参数
     * @return 结果
     */
    public WxMaJscode2SessionResult preProcess(String core, String... params) {
        WxMaJscode2SessionResult wxMaSession = wxappProcessor.login(core, params[0]);
        if (ObjectUtils.isNotEmpty(wxMaSession)) {
           return wxMaSession;
        }
        throw new AccessPreProcessFailedException("WxApp login failed");
    }

}
