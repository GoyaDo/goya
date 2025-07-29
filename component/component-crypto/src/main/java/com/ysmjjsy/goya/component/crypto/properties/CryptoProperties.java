package com.ysmjjsy.goya.component.crypto.properties;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.component.crypto.enums.CryptoStrategy;
import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 加密算法配置 </p>
 *
 * @author goya
 * @since 2022/5/1 21:13
 */
@Setter
@Getter
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_ASSISTANT_CRYPTO)
public class CryptoProperties {

    /**
     * 加密算法策略，默认：国密算法
     */
    private CryptoStrategy cryptoStrategy = CryptoStrategy.SM;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("strategy", cryptoStrategy)
                .toString();
    }
}
