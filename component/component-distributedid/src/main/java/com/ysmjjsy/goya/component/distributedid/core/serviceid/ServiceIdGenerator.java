package com.ysmjjsy.goya.component.distributedid.core.serviceid;

import com.ysmjjsy.goya.component.distributedid.core.IdGenerator;
import com.ysmjjsy.goya.component.distributedid.core.snowflake.SnowflakeIdInfo;

/**
 * <p>业务 ID 生成器</p>
 *
 * @author goya
 * @since 2025/7/25 00:30
 */
public interface ServiceIdGenerator extends IdGenerator {

    /**
     * 根据 {@param serviceId} 生成雪花算法 ID
     */
    long nextId(long serviceId);

    /**
     * 根据 {@param serviceId} 生成字符串类型雪花算法 ID
     */
    String nextIdStr(long serviceId);

    /**
     * 解析雪花算法
     */
    SnowflakeIdInfo parseSnowflakeId(long snowflakeId);
}
