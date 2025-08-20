package com.ysmjjsy.goya.module.justauth.stamp;

import cn.hutool.v7.core.data.id.IdUtil;
import com.ysmjjsy.goya.component.cache.stamp.AbstractStampManager;
import com.ysmjjsy.goya.module.justauth.constants.JustAuthConstants;
import com.ysmjjsy.goya.module.justauth.properties.JustAuthProperties;
import lombok.Setter;
import me.zhyd.oauth.cache.AuthStateCache;

import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 自定义JustAuth State Cache </p>
 *
 * @author goya
 * @since 2021/5/22 10:22
 */
@Setter
public class JustAuthStateStampManager extends AbstractStampManager<String, String> implements AuthStateCache {

    public JustAuthStateStampManager() {
        super(JustAuthConstants.CACHE_NAME_TOKEN_JUSTAUTH);
    }

    private JustAuthProperties justAuthProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(justAuthProperties.getTimeout());
    }

    @Override
    public String nextStamp(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void cache(String key, String value) {
        this.put(key, value);
    }

    @Override
    public void cache(String key, String value, long expire) {
        this.put(key, value, expire, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean containsKey(String key) {
        return this.containKey(key);
    }
}
