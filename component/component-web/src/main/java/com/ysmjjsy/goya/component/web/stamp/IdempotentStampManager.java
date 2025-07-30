package com.ysmjjsy.goya.component.web.stamp;

import com.ysmjjsy.goya.component.cache.jetcache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.component.web.constants.WebConstants;
import com.ysmjjsy.goya.component.web.configuration.properties.SecureProperties;
import lombok.Getter;
import org.dromara.hutool.core.data.id.IdUtil;

/**
 * <p>Description: 幂等Stamp管理 </p>
 *
 * @author goya
 * @since 2021/8/22 16:05
 */
@Getter
public class IdempotentStampManager extends AbstractStampManager<String, String> {

    private final SecureProperties secureProperties;

    public IdempotentStampManager(SecureProperties secureProperties) {
        super(WebConstants.CACHE_NAME_TOKEN_IDEMPOTENT);
        this.secureProperties = secureProperties;
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(secureProperties.getIdempotent().getExpire());
    }
}
