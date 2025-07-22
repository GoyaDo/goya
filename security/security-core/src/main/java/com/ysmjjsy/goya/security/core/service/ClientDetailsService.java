package com.ysmjjsy.goya.security.core.service;

import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;

import java.util.Set;

/**
 * <p>Description: 客户端操作基础接口 </p>
 *
 * @author goya
 * @since 2022/4/1 18:16
 */
public interface ClientDetailsService {

    /**
     * 根据客户端ID获取客户端权限
     *
     * @param clientId 客户端ID
     * @return 客户端权限集合
     */
    Set<GoyaGrantedAuthority> findAuthoritiesById(String clientId);
}
