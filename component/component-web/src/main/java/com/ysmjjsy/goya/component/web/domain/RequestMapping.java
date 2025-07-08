package com.ysmjjsy.goya.component.web.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.dto.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>Description: Controller 请求注解元数据封装实体 </p>
 *
 * @author goya
 * @since 2020/6/2 19:52
 */
@Setter
@Getter
public class RequestMapping implements Entity {

    @Serial
    private static final long serialVersionUID = 4675282878684772378L;
    
    private String mappingId;

    private String mappingCode;

    private String requestMethod;

    private String serviceId;

    private String className;

    private String methodName;

    private String url;

    private String description;

    public RequestMapping() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestMapping that = (RequestMapping) o;
        return Objects.equal(mappingId, that.mappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mappingId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mappingId", mappingId)
                .add("mappingCode", mappingCode)
                .add("requestMethod", requestMethod)
                .add("serviceId", serviceId)
                .add("className", className)
                .add("methodName", methodName)
                .add("url", url)
                .add("description", description)
                .toString();
    }
}
