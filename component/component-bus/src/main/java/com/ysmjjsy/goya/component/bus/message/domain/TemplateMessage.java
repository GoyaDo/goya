package com.ysmjjsy.goya.component.bus.message.domain;

import lombok.Data;

import java.io.Serial;

/**
 * <p>Description: Spring Messaging Template 类型消息参数实体 </p>
 *
 * @author goya
 * @since 2023/10/26 14:55
 */
@Data
public class TemplateMessage implements Message<Object> {

    @Serial
    private static final long serialVersionUID = 1310984905498066925L;

    private String userId;

    private Object payload;

}
