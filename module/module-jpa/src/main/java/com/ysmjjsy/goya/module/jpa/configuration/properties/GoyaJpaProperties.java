package com.ysmjjsy.goya.module.jpa.configuration.properties;

import com.ysmjjsy.goya.module.jpa.constants.GoyaJpaConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/14 17:34
 */
@ConfigurationProperties(prefix = GoyaJpaConstants.PROPERTY_PREFIX_DB_JPA)
public record GoyaJpaProperties(
        Repository repository,
        Entity entity
) {

    public record Repository(
            String[] basePackages
    ){
    }

    public record Entity(
            String[] basePackages
    ){
    }
}
