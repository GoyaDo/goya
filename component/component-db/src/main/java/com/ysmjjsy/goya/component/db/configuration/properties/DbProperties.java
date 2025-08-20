package com.ysmjjsy.goya.component.db.configuration.properties;

import com.ysmjjsy.goya.component.db.enums.RepositoryMode;
import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/8/1 10:04
 */
@Data
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_PREFIX_DB)
public class DbProperties {

    /**
     * 持久化类型
     */
    private RepositoryMode mode;
}
