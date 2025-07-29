package com.ysmjjsy.goya.module.wechat.constants;

import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.pojo.constants.GoyaConstants.PROPERTY_ENABLED;


/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/17 11:30
 */
public interface WeChatConstants {

    String PROPERTY_ACCESS_WECHAT = GoyaConstants.GOYA + ".wechat";
    String PROPERTY_ACCESS_WXAPP = PROPERTY_ACCESS_WECHAT + ".app";
    String ITEM_WXAPP_ENABLED = PROPERTY_ACCESS_WXAPP + PROPERTY_ENABLED;
    String PROPERTY_ACCESS_WXMPP = PROPERTY_ACCESS_WECHAT + ".mpp";
    String ITEM_WXMPP_ENABLED = PROPERTY_ACCESS_WXMPP + PROPERTY_ENABLED;
}
