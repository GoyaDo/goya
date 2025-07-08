package com.ysmjjsy.goya.component.common.api;

import cn.zhxu.okhttps.HTTP;
import cn.zhxu.okhttps.MsgConvertor;
import cn.zhxu.okhttps.jackson.JacksonMsgConvertor;

/**
 * <p>Description: 外部 Rest API 集成抽象服务 </p>
 *
 * @author goya
 * @since : 2021/4/10 15:33
 */
public interface RestApiTemplate {

    /**
     * 获取外部Rest API基础地址
     *
     * @return 访问接口的统一BaseURL
     */
    String getUrl();

    default HTTP http() {
        return HTTP.builder()
                .baseUrl(getUrl())
                .addMsgConvertor(getMsgConvertor())
                .build();
    }

    default MsgConvertor getMsgConvertor() {
        return new JacksonMsgConvertor();
    }
}
