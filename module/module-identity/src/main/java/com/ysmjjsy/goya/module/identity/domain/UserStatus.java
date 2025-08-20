package com.ysmjjsy.goya.module.identity.domain;

import com.ysmjjsy.goya.component.common.pojo.domain.DTO;

/**
 * <p>Description: 用户状态变更实体 </p>
 *
 * @author goya
 * @since 2022/7/10 16:15
 */
public record UserStatus(
        String userId,
        String status
) implements DTO {
}
