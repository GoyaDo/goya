package com.ysmjjsy.goya.module.identity.domain;

import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.common.pojo.enums.DataItemStatus;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * <p>Description: 用户登录信息 </p>
 *
 * @author goya
 * @since 2022/7/13 14:31
 */
public record UserPrincipal(
        /*
          用户ID
         */
        String userId,

        /*
          用户名
         */
        String username,

        /*
          昵称
         */
        String nickname,

        /*
          手机号
         */
        String phoneNumber,

        /*
          邮箱
         */
        String email,

        /*
          用户头像
         */
        String avatar,

        /*
          密码
         */
        String password,

        /*
          角色信息
         */
        Set<UserRole> roles,

        /*
          账号状态
         */
        DataItemStatus status,

        /*
          账号过期时间
         */
        LocalDateTime accountExpireAt,

        /*
          凭证过期时间
         */
        LocalDateTime credentialsExpireAt

) implements GoyaPrincipal {

    /**
     * Principal 接口要求实现的方法。
     * 这里返回用户名作为唯一标识。
     */
    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equal(email(), that.email()) && Objects.equal(userId(), that.userId()) && Objects.equal(avatar(), that.avatar()) && Objects.equal(username(), that.username()) && Objects.equal(nickname(), that.nickname()) && Objects.equal(password(), that.password()) && Objects.equal(phoneNumber(), that.phoneNumber()) && Objects.equal(roles(), that.roles()) && status() == that.status() && Objects.equal(accountExpireAt(), that.accountExpireAt()) && Objects.equal(credentialsExpireAt(), that.credentialsExpireAt());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId(), username(), nickname(), phoneNumber(), email(), avatar(), password(), roles(), status(), accountExpireAt(), credentialsExpireAt());
    }
}