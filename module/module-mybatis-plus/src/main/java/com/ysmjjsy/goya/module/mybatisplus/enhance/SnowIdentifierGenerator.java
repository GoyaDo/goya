package com.ysmjjsy.goya.module.mybatisplus.enhance;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.ysmjjsy.goya.component.distributedid.utils.SnowflakeIdUtil;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 17:17
 */
public class SnowIdentifierGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return SnowflakeIdUtil.nextId();
    }
}
