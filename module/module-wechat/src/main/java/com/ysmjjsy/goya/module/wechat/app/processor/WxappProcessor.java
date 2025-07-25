package com.ysmjjsy.goya.module.wechat.app.processor;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.*;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.google.common.collect.Maps;
import com.ysmjjsy.goya.module.wechat.properties.WxAppProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 微信小程序核心基础代码 </p>
 *
 * @author goya
 * @since 2021/5/27 20:29
 */
public class WxappProcessor {

    private static final Logger log = LoggerFactory.getLogger(WxappProcessor.class);

    private final WxAppProperties wxAppProperties;
    private final WxappLogHandler wxappLogHandler;
    private final Map<String, WxMaMessageRouter> wxMaMessageRouters;
    private final Map<String, WxMaService> wxMaServices;

    public WxappProcessor(WxAppProperties wxAppProperties) {
        this.wxAppProperties = wxAppProperties;
        this.wxappLogHandler = new WxappLogHandler();
        this.wxMaMessageRouters = Maps.newHashMap();
        this.wxMaServices = initWxMaServices(this.wxAppProperties, this.wxMaMessageRouters);
    }

    private Map<String, WxMaService> initWxMaServices(WxAppProperties wxAppProperties, Map<String, WxMaMessageRouter> wxMaMessageRouters) {
        List<WxAppProperties.Config> configs = wxAppProperties.getConfigs();

        if (CollectionUtils.isNotEmpty(configs)) {

            log.info("[Goya] |- Bean [Weixin Mini App] Configure.");

            return configs.stream()
                    .map(a -> {
                        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
//                WxMaDefaultConfigImpl config = new WxMaRedisConfigImpl(new JedisPool());
                        // 使用上面的配置时，需要同时引入jedis-lock的依赖，否则会报类无法找到的异常
                        config.setAppid(a.getAppId());
                        config.setSecret(a.getSecret());
                        config.setToken(a.getToken());
                        config.setAesKey(a.getAesKey());
                        config.setMsgDataFormat(a.getMessageDataFormat());

                        WxMaService service = new WxMaServiceImpl();
                        service.setWxMaConfig(config);
                        wxMaMessageRouters.put(a.getAppId(), this.newRouter(service));
                        return service;
                    }).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));
        } else {
            throw new WxRuntimeException("Weixin Mini App Configuraiton is not setting!");
        }
    }

    private WxMaMessageRouter newRouter(WxMaService wxMaService) {
        final WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        router.rule().handler(wxappLogHandler).next();
        return router;
    }

    /**
     * 根据 Appid 获取到 {@link WxMaService} 对象
     *
     * @param appid 小程序 AppId
     * @return {@link WxMaService} 对象
     */
    public WxMaService getWxMaService(String appid) {
        WxMaService wxMaService = wxMaServices.get(appid);
        if (ObjectUtils.isEmpty(wxMaService)) {
            throw new IllegalArgumentException(String.format("Cannot find the configuration of appid=[%s], please check!", appid));
        }
        return wxMaService;
    }

    /**
     * 根据 Appid 获取到 {@link WxMaMessageRouter} 对象
     *
     * @param appid 小程序 AppId
     * @return {@link WxMaMessageRouter} 对象
     */
    public WxMaMessageRouter getWxMaMessageRouter(String appid) {
        return wxMaMessageRouters.get(appid);
    }

    /**
     * 根据默认的 AppId 获取对应的 {@link WxMaService} 对象
     *
     * @return {@link WxMaService} 对象
     */
    public WxMaService getWxMaService() {
        String appId = wxAppProperties.getDefaultAppId();
        if (StringUtils.isBlank(appId)) {
            log.error("[Goya] |- Must set [goya.platform.social.wxapp.default-app-id] property, or use getWxMaService(String appid)!");
            throw new IllegalArgumentException("Must set [goya.platform.social.wxapp.default-app-id] property");
        }
        return this.getWxMaService(appId);
    }

    /**
     * 获取登录后的session信息.
     *
     * @param code        登录时获取的 code
     * @param wxMaService 微信小程序服务
     * @return {@link WxMaJscode2SessionResult}
     */
    private WxMaJscode2SessionResult getSessionInfo(String code, WxMaService wxMaService) {
        try {
            WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(code);
            log.debug("[Goya] |- Weixin Mini App login successfully!");
            return sessionResult;
        } catch (WxErrorException e) {
            log.error("[Goya] |- Weixin Mini App login failed! For reason: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 使用 code 和 appId，登录微信小程序
     *
     * @param code  小程序生成的 code
     * @param appId 小程序 AppId
     * @return {@link WxMaJscode2SessionResult} 对象
     */
    public WxMaJscode2SessionResult login(String code, String appId) {
        WxMaService wxMaService = getWxMaService(appId);
        if (StringUtils.isNotBlank(code) && ObjectUtils.isNotEmpty(wxMaService)) {
            return this.getSessionInfo(code, wxMaService);
        } else {
            log.error("[Goya] |- Weixin Mini App login failed, please check code param!");
            return null;
        }
    }

    /**
     * 验证用户完整性
     *
     * @param sessionKey  会话密钥
     * @param rawData     微信用户基本信息
     * @param signature   数据签名
     * @param wxMaService 微信小程序服务
     * @return true 完整， false 不完整
     */
    private boolean checkUserInfo(String sessionKey, String rawData, String signature, WxMaService wxMaService) {
        if (wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            log.debug("[Goya] |- Weixin Mini App user info is valid!");
            return true;
        } else {
            log.warn("[Goya] |- Weixin Mini App user check failed!");
            return false;
        }
    }

    private boolean checkUserInfo(String rawData, String signature) {
        return StringUtils.isNotBlank(rawData) && StringUtils.isNotBlank(signature);
    }

    /**
     * 解密用户信息
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param iv            加密算法的初始向量
     * @param wxMaService   微信小程序服务
     * @return {@link WxMaUserInfo}
     */
    private WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        log.debug("[Goya] |- Weixin Mini App get user info successfully!");
        return wxMaUserInfo;
    }

    public WxMaUserInfo getUserInfo(String appId, String sessionKey, String encryptedData, String iv) {
        return this.getUserInfo(appId, sessionKey, encryptedData, iv, null, null);
    }

    public WxMaUserInfo getUserInfo(String appId, String sessionKey, String encryptedData, String iv, String rawData, String signature) {
        WxMaService wxMaService = getWxMaService(appId);

        if (ObjectUtils.isNotEmpty(wxMaService)) {
            // 用户信息校验
            if (checkUserInfo(rawData, signature)) {
                if (checkUserInfo(sessionKey, rawData, signature, wxMaService)) {
                    return null;
                }
            }
            return this.getUserInfo(sessionKey, encryptedData, iv, wxMaService);
        } else {
            log.error("[Goya] |- Weixin Mini App get user info failed!");
            return null;
        }
    }

    /**
     * 解密手机号
     * <p>
     * 确认下前后端传递的参数有没有做UrlEncode/UrlDecode，因为encryptedData里会包含特殊字符在传递参数时被转义，可能服务器端实际拿到的参数encryptedData并不是前端实际获取到的值，导致SDK调用微信相应接口时无法解密而报错，只要保证前端实际获取到的encryptedData和服务器端调用SDK时传入的encryptedData一致就不会报错的，SDK中方法并无问题；建议让前后台都打印下日志，看下服务端最终使用的参数值是否还是前端获取到的原始值呢。PS：SpringBoot某些场景下form表单参数是会自动做UrlDecode的...
     * <p>
     * {@see :https://github.com/Wechat-Group/WxJava/issues/359}
     *
     * @param code        会话密钥
     * @param wxMaService 微信小程序服务
     * @return {@link WxMaPhoneNumberInfo}
     */
    private WxMaPhoneNumberInfo getPhoneNumberInfo(String code, WxMaService wxMaService) {
        log.info("[Goya] |- Weixin Mini App get code： {}", code);

        WxMaPhoneNumberInfo wxMaPhoneNumberInfo;
        try {
            wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNumber(code);
            log.debug("[Goya] |- Weixin Mini App get phone number successfully! WxMaPhoneNumberInfo : {}", wxMaPhoneNumberInfo.toString());
            return wxMaPhoneNumberInfo;
        } catch (Exception e) {
            log.error("[Goya] |- Weixin Mini App get phone number failed!");
            return null;
        }
    }

    public WxMaPhoneNumberInfo getPhoneNumberInfo(String appId, String sessionKey, String rawData, String signature, String code) {

        WxMaService wxMaService = getWxMaService(appId);

        if (ObjectUtils.isNotEmpty(wxMaService)) {
            // 用户信息校验
            if (checkUserInfo(rawData, signature)) {
                if (checkUserInfo(sessionKey, rawData, signature, wxMaService)) {
                    return null;
                }
            }

            return this.getPhoneNumberInfo(code, wxMaService);
        } else {
            log.error("[Goya] |- Weixin Mini App get phone number info failed!");
            return null;
        }
    }

    /**
     * 根据直接创建的WxMaSubscribeMessage发送订阅消息
     *
     * @param appId            小程序appId
     * @param subscribeMessage 参见 {@link WxMaSubscribeMessage}
     * @return true 发送成功，false 发送失败，或者参数subscribeId配置不对，无法获取相应的WxMaSubscribeMessage
     */
    public boolean sendSubscribeMessage(String appId, WxMaSubscribeMessage subscribeMessage) {
        try {
            this.getWxMaService(appId).getMsgService().sendSubscribeMsg(subscribeMessage);
            log.debug("[Goya] |- Send Subscribe Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Goya] |- Send Subscribe Message Failed!", e);
            return false;
        }
    }

    /**
     * 检查一段文本是否含有违法违规内容。
     * 应用场景举例：
     * · 用户个人资料违规文字检测；
     * · 媒体新闻类用户发表文章，评论内容检测；
     * · 游戏类用户编辑上传的素材(如答题类小游戏用户上传的问题及答案)检测等。 频率限制：单个 appId 调用上限为 4000 次/分钟，2,000,000 次/天*
     * · 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/msgSecCheck.html">...</a>
     *
     * @param appId   小程序appId
     * @param message 需要检测的字符串
     * @return 是否违规 boolean
     */
    public boolean checkMessage(String appId, String message) {
        try {
            this.getWxMaService(appId).getSecurityService().checkMessage(message);
            log.debug("[Goya] |- Check Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Goya] |- Check Message Failed!", e);
            return false;
        }
    }

    /**
     * 校验一张图片是否含有违法违规内容
     *
     * @param appId   小程序appId
     * @param fileUrl 需要检测图片的网地址
     * @return 是否违规 boolean
     */
    public boolean checkImage(String appId, String fileUrl) {
        try {
            this.getWxMaService(appId).getSecurityService().checkImage(fileUrl);
            log.debug("[Goya] |- Check Image use fileUrl Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Goya] |- Check Image use fileUrl Failed! Detail is ：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 校验一张图片是否含有违法违规内容.
     * <p>
     * 应用场景举例：
     * 1）图片智能鉴黄：涉及拍照的工具类应用(如美拍，识图类应用)用户拍照上传检测；电商类商品上架图片检测；媒体类用户文章里的图片检测等；
     * 2）敏感人脸识别：用户头像；媒体类用户文章里的图片检测；社交类用户上传的图片检测等。频率限制：单个 appId 调用上限为 1000 次/分钟，100,000 次/天
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/imgSecCheck.html">...</a>
     *
     * @param appId 小程序appId
     * @param file  图片文件
     * @return 是否违规 boolean
     */
    public boolean checkImage(String appId, File file) {
        try {
            this.getWxMaService(appId).getSecurityService().checkImage(file);
            log.debug("[Goya] |- Check Image use file Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Goya] |- Check Image use file Failed! Detail is ：{}", e.getMessage());
            return false;
        }
    }

    /**
     * 异步校验图片/音频是否含有违法违规内容。
     * 应用场景举例：
     * 语音风险识别：社交类用户发表的语音内容检测；
     * 图片智能鉴黄：涉及拍照的工具类应用(如美拍，识图类应用)用户拍照上传检测；电商类商品上架图片检测；媒体类用户文章里的图片检测等；
     * 敏感人脸识别：用户头像；媒体类用户文章里的图片检测；社交类用户上传的图片检测等。
     * 频率限制：
     * 单个 appId 调用上限为 2000 次/分钟，200,000 次/天；文件大小限制：单个文件大小不超过10M
     * 详情请见:
     * <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/sec-check/security.mediaCheckAsync.html">...</a>
     *
     * @param appId     小程序appId
     * @param mediaUrl  要检测的多媒体url
     * @param mediaType 媒体类型 {@link cn.binarywang.wx.miniapp.constant.WxMaConstants.SecCheckMediaType}
     * @return 微信检测结果 WxMaMediaAsyncCheckResult {@link WxMaMediaAsyncCheckResult}
     */
    public WxMaMediaAsyncCheckResult mediaAsyncCheck(String appId, String mediaUrl, int mediaType) {
        WxMaMediaAsyncCheckResult wxMaMediaAsyncCheckResult = null;
        try {
            wxMaMediaAsyncCheckResult = this.getWxMaService(appId).getSecurityService().mediaCheckAsync(mediaUrl, mediaType);
            log.debug("[Goya] |- Media Async Check Successfully!");
        } catch (WxErrorException e) {
            log.debug("[Goya] |- Media Async Check Failed! Detail is ：{}", e.getMessage());
        }

        return wxMaMediaAsyncCheckResult;
    }
}
