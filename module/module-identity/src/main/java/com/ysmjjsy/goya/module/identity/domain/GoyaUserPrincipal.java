package com.ysmjjsy.goya.module.identity.domain;

import lombok.Data;

import java.util.Set;

/**
 * <p>Description: 用户登录信息 </p>
 *
 * @author goya
 * @since 2022/7/13 14:31
 */
@Data
public class GoyaUserPrincipal implements GoyaPrincipal {

    private String id;

    private String name;

    private String avatar;

    private Set<String> roles;

    public GoyaUserPrincipal() {
    }

    public GoyaUserPrincipal(String id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
