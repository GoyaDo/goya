package com.ysmjjsy.goya.security.core.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

/**
 * <p>Description: 自定义 GrantedAuthority </p>
 *
 * @author goya
 * @since 2022/3/5 0:12
 */
public class GoyaGrantedAuthority implements GrantedAuthority {

    @Serial
    private static final long serialVersionUID = 4867534482587875836L;
    
    private String authority;

    public GoyaGrantedAuthority() {
    }

    public GoyaGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoyaGrantedAuthority that = (GoyaGrantedAuthority) o;
        return Objects.equal(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("authority", authority)
                .toString();
    }
}