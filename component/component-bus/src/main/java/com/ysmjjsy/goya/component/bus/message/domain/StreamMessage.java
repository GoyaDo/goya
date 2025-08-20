package com.ysmjjsy.goya.component.bus.message.domain;

import lombok.Data;
import org.springframework.util.MimeType;

import java.io.Serial;

/**
 * <p>Description: Spring Cloud Stream 类型消息参数实体 </p>
 *
 * @author goya
 * @since 2023/10/26 14:57
 */
@Data
public class StreamMessage implements Message<Object> {

    @Serial
    private static final long serialVersionUID = 5137221128985789325L;

    private String bindingName;
    private String binderName;
    private Object payload;
    private MimeType outputContentType;
}
