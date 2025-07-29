package com.ysmjjsy.goya.security.authentication.client.compliance;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.ysmjjsy.goya.component.pojo.domain.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p>Description: 用户状态变更实体 </p>
 *
 * @author goya
 * @since 2022/7/10 16:15
 */
@Setter
@Getter
public class UserStatus implements Entity {

    @Serial
    private static final long serialVersionUID = -7973270070387291560L;

    private String userId;

    private String status;

    public UserStatus() {
    }

    public UserStatus(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserStatus that = (UserStatus) o;
        return Objects.equal(userId, that.userId) && Objects.equal(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId, status);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("status", status)
                .toString();
    }
}
