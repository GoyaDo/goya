package com.ysmjjsy.goya.component.db.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/30 10:12
 */
@Getter
@Setter
public abstract class BaseTenantDbEntity extends BaseDbEntity {

    @Serial
    private static final long serialVersionUID = -2222983429790563721L;

    /**
     * tenant
     */
    protected String tenant;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseTenantDbEntity that = (BaseTenantDbEntity) o;
        return Objects.equal(getTenant(), that.getTenant());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getTenant());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tenant", tenant)
                .add("id", id)
                .add("createdTime", createdTime)
                .add("updatedTime", updatedTime)
                .add("createBy", createBy)
                .add("updateBy", updateBy)
                .add("version", version)
                .add("delFlag", delFlag)
                .toString();
    }
}
