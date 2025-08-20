package com.ysmjjsy.goya.component.secure.stamp;

import cn.hutool.v7.core.data.id.IdUtil;
import com.ysmjjsy.goya.component.cache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.component.secure.configuration.properties.SecureProperties;
import com.ysmjjsy.goya.component.web.constants.WebConstants;
import lombok.Getter;

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
