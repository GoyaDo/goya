package com.ysmjjsy.goya.security.authorization.domain;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>Description: Security Metadata 传输数据实体 </p>
 *
 * @author goya
 * @since 2021/8/8 15:51
 */
@Data
public class AttributeTransmitter implements Serializable {

    @Serial
    private static final long serialVersionUID = 2181292742254496643L;

    private String attributeId;

    private String attributeCode;

    private String attributeName;

    private String webExpression;

    private String permissions;

    private String url;

    private String requestMethod;

    private String serviceId;

    private String className;

    private String methodName;

}
