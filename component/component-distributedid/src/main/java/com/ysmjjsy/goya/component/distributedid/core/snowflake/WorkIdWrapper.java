package com.ysmjjsy.goya.component.distributedid.core.snowflake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>WorkId 包装器</p>
 *
 * @author goya
 * @since 2025/7/25 00:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkIdWrapper {

    /**
     * 工作ID
     */
    private Long workId;

    /**
     * 数据中心ID
     */
    private Long dataCenterId;
}
