package com.ysmjjsy.goya.component.doc.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Swagger 自定义配置 </p>
 *
 * @author goya
 * @since 2023/5/9 18:45
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_PREFIX_DOC)
public class SwaggerProperties {

    /**
     * 是否开启Swagger
     */
    private Boolean enabled;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .toString();
    }
}
